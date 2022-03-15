import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import kotlin.Exception
import kotlin.random.Random
import kotlin.system.exitProcess

class BidderMicroservice {
    private var auctioneerSocket: Socket
    private var auctionResultObservable: Observable<String>
    private var myIdentity: String = "[BIDDER_NECONECTAT]"

    companion object Constants {
        const val AUCTIONEER_HOST = "localhost"
        const val AUCTIONEER_PORT = 1500
        const val MAX_BID = 10_000
        const val MIN_BID = 1_000
        val names : List<String> = arrayListOf(
            "Catina Dalca", "Mariana Vasilescu", "Adina Constantin",
            "Anghel Vladimirescu", "Dorinel Lungu", "Ignat Vasile",
            "Liana Antonescu", "Cătălina Alexandrescu", "Iuliu Lupu",
            "Atanase Șerban", "Rodica Bălan", "Marian Bălan",
            "Ilinca Bălan", "Ion Vasile", "Claudia Dumitru",
            "Diana Nicolescu", "Steliana Ionescu", "Eugenia Dumitrescu",
            "Diana Lupei", "Dănuț Constantin", "Grigore Șerban",
            "Ivan Șerban", "Gheorghe Nicolescu", "Mihăiță Lupu",
            "Mihaela Popescu", "Gabriel Dumitru", "Iancu Șerban",
            "Miron Lupei", "Virginia Grigorescu", "Neculai Albert",
            "Catrinel Petran", "Marina Alexandrescu", "Răzvan Popescu",
            "Isac Albescu", "Ileana Adam", "Ilie Popescu",
            "Denis Popescu", "Lidia Albert", "Violeta Albu",
            "Valeria Nicolescu", "Manuel Lupu", "Costin Funar",
            "Daria Dalca", "Dragomir Nicolescu", "Simon Ionesco",
            "Ovidiu Grigorescu", "Bogdana Adam", "Daniel Cojocaru",
            "Mariana Funar", "Petre Anghelescu", "Stefan Stratulat"
        )
        val phoneNumbers : List<String> = arrayListOf(
            "(751) 796-5830", "(987) 700-3290", "(362) 718-6234",
            "(869) 770-5796", "(752) 586-4166", "(515) 591-3703",
            "(522) 429-6137", "(374) 679-0466", "(207) 978-6865",
            "(899) 558-7116", "(459) 867-3694", "(238) 476-8197",
            "(963) 347-5286", "(570) 268-2780", "(806) 523-3961",
            "(310) 897-5823", "(689) 824-5680", "(228) 561-6345",
            "(285) 660-4926", "(596) 212-3626", "(286) 942-0927",
            "(391) 804-8616", "(395) 303-2760", "(761) 777-0135",
            "(783) 200-7973", "(340) 965-5050", "(540) 383-1076",
            "(503) 585-1647", "(473) 395-3291", "(778) 911-3985",
            "(397) 468-3724", "(906) 340-7471", "(474) 677-9095",
            "(469) 961-9641", "(680) 332-7041", "(772) 329-0627",
            "(390) 811-7196", "(907) 922-0884", "(578) 261-5826",
            "(461) 389-0187", "(598) 355-5376", "(848) 367-8262",
            "(877) 877-0252", "(834) 735-6298", "(724) 583-6396",
            "(809) 853-1429", "(641) 594-3406", "(855) 359-6716",
            "(295) 758-9360", "(425) 925-3359", "(987) 700-3290"
        )
    }

    init {
        try {
            auctioneerSocket = Socket(AUCTIONEER_HOST, AUCTIONEER_PORT)
            print("\nM-am conectat la Auctioneer!")

            myIdentity = "[${auctioneerSocket.localPort}]"

            // se creeaza un obiect Observable ce va emite mesaje primite printr-un TCP
            // fiecare mesaj primit reprezinta un element al fluxului de date reactiv
            auctionResultObservable = Observable.create<String> { emitter ->
                // se citeste raspunsul de pe socketul TCP
                val bufferReader = BufferedReader(InputStreamReader(auctioneerSocket.inputStream))
                val receivedMessage = bufferReader.readLine()

                // daca se primeste un mesaj gol (NULL), atunci inseamna ca cealalta parte a socket-ului a fost inchisa
                if (receivedMessage == null) {
                    bufferReader.close()
                    auctioneerSocket.close()

                    emitter.onError(Exception("AuctioneerMicroservice s-a deconectat."))
                    return@create
                }

                // mesajul primit este emis in flux
                emitter.onNext(receivedMessage)

                // deoarece se asteapta un singur mesaj, in continuare se emite semnalul de incheiere al fluxului
                emitter.onComplete()

                bufferReader.close()
                auctioneerSocket.close()
            }
        } catch (e: Exception) {
            print("\n$myIdentity Nu ma pot conecta la Auctioneer!")
            exitProcess(1)
        }
    }

    private fun bid() {
        // se genereaza o oferta aleatorie din partea bidderului curent
        val pret = Random.nextInt(MIN_BID, MAX_BID)
        val randomIndex = Random.nextInt(0,50)
        val name = BidderMicroservice.names[randomIndex]
        val phoneNumber = BidderMicroservice.phoneNumbers[randomIndex]
        val email = name.replace(" ", "_") + "@gmail.com";

        // se creeaza mesajul care incapsuleaza oferta
        val biddingMessage = Message.create("${auctioneerSocket.localAddress}:${auctioneerSocket.localPort}",
            "[$name, $phoneNumber, $email] licitez $pret")

        // bidder-ul trimite pretul pentru care doreste sa liciteze
        val serializedMessage = biddingMessage.serialize()
        auctioneerSocket.getOutputStream().write(serializedMessage)

        // exista o sansa din 2 ca bidder-ul sa-si trimita oferta de 2 ori, eronat
        if (Random.nextBoolean()) {
            auctioneerSocket.getOutputStream().write(serializedMessage)
        }
    }

    private fun waitForResult() {
        print("\n$myIdentity Astept rezultatul licitatiei...")
        // bidder-ul se inscrie pentru primirea unui raspuns la oferta trimisa de acesta
        val auctionResultSubscription = auctionResultObservable.subscribeBy(
            // cand se primeste un mesaj in flux, inseamna ca a sosit rezultatul licitatiei
            onNext = {
                val resultMessage: Message = Message.deserialize(it.toByteArray())
                print("\n$myIdentity Rezultat licitatie: ${resultMessage.body}")
            },
            onError = {
                print("\n$myIdentity Eroare: $it")
            }
        )

        // se elibereaza memoria obiectului Subscription
        auctionResultSubscription.dispose()
    }

    fun run() {
        bid()
        waitForResult()
    }
}

fun main(args: Array<String>) {
    val bidderMicroservice = BidderMicroservice()
    bidderMicroservice.run()
}