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
    private var gradesSocket:Socket

    private var myIdentity: String = "[BIDDER_NECONECTAT]"

    companion object Constants {
        const val AUCTIONEER_HOST = "localhost"
        const val AUCTIONEER_PORT = 1500
        const val GRADES_PORT = 1900
        const val MAX_BID = 10_000
        const val MIN_BID = 1_000
    }
    init {
        try
        {
            auctioneerSocket = Socket(AUCTIONEER_HOST, AUCTIONEER_PORT)
            println("M-am conectat la Auctioneer!")
            myIdentity = "[${auctioneerSocket.localPort}"
            auctionResultObservable = Observable.create<String>{
                emitter->
                val bufferedReader = BufferedReader(InputStreamReader(auctioneerSocket.inputStream))
                val receivedMessage = bufferedReader.readLine()

                if(receivedMessage==null)
                {
                    bufferedReader.close()
                    auctioneerSocket.close()
                    emitter.onError(java.lang.Exception("Auctioneer s-a deconectat!!!"))
                    return@create

                }
                emitter.onNext(receivedMessage)
                emitter.onComplete()
                bufferedReader.close()
                auctioneerSocket.close()
            }
        }
        catch (e:Exception)
        {
            println("$myIdentity NU ma pot conecta la Auctioneer")
            exitProcess(1)
        }
        try {
            gradesSocket = Socket(AUCTIONEER_HOST, GRADES_PORT)
            println("Evaluez licitatia!")

        }
        catch (e:Exception)
        {
            println("$myIdentity nu pot evalua licitatia!")
            exitProcess(1)
        }

    }
    private fun bid() {
// se genereaza o oferta aleatorie din partea bidderului curent
        val pret = Random.nextInt(MIN_BID, MAX_BID)
// se creeaza mesajul care incapsuleaza oferta
        val biddingMessage = Message.create("${auctioneerSocket.localAddress}:${auctioneerSocket.localPort}", "licitez $pret")
// bidder-ul trimite pretul pentru care doreste sa liciteze
                val serializedMessage = biddingMessage.serialize()
                 auctioneerSocket.getOutputStream().write(serializedMessage)
                if (Random.nextBoolean()) {
                    auctioneerSocket.getOutputStream().write(serializedMessage)
                }
    }
                
    private fun waitForResult() {
        println("$myIdentity Astept rezultatul licitatiei...")
// bidder-ul se inscrie pentru primirea unui raspuns la oferta trimisa de acesta
        val auctionResultSubscription =
            auctionResultObservable.subscribeBy(
// cand se primeste un mesaj in flux, inseamna ca a sosit rezultatul licitatiei
                        onNext = {
                    val resultMessage: Message =
                        Message.deserialize(it.toByteArray())
                    println("$myIdentity Rezultat licitatie:${resultMessage.body}")
                },
                onError = {
                    println("$myIdentity Eroare: $it")
                }
            )
// se elibereaza memoria obiectului Subscription
        auctionResultSubscription.dispose()
    }
    private fun evaluate()
    {
        // se genereaza o oferta aleatorie din partea bidderului curent
        val grades = Random.nextInt(1, 5)
// se creeaza mesajul care incapsuleaza oferta
        val biddingMessage = Message.create("${auctioneerSocket.localAddress}:${auctioneerSocket.localPort}", "dau nota  $grades")
// bidder-ul trimite pretul pentru care doreste sa liciteze
        val serializedMessage = biddingMessage.serialize()
        gradesSocket.getOutputStream().write(serializedMessage)
        /*if (Random.nextBoolean()) {
            gradesSocket.getOutputStream().write(serializedMessage)
        }  */
    }

    fun run() {
        bid()
        waitForResult()
        evaluate()
    }
}
fun main(args: Array<String>) {
    val bidderMicroservice = BidderMicroservice()
    bidderMicroservice.run()
}
