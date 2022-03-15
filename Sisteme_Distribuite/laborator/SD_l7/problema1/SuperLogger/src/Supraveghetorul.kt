import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.util.*

class Supraveghetorul {
    private lateinit var serverSupraveghetor : ServerSocket /*serverul*/
    /*cate un socket pentru fiecare microserviciu*/
    private lateinit var auctioneerSocket: Socket
    private lateinit var bidderSocket: Socket
    private lateinit var biddingProcessSocket: Socket
    private lateinit var messageProcessorSocket: Socket
    private var receiveMessageObservable: Observable<String>
    private val subscriptions = CompositeDisposable()
    private val messageQueue: Queue<Message> = LinkedList<Message>()
    //fisierul in care voi adauga informatiile
    private var myFile = File("/home/ana/Desktop/SD_l7/problema1/log")
    private var conexiuniInchise :Int = 0

    //atribui numere de porturi pentru toate conexiunile pe care le voi crea
    //pornesc serverul pe portul 1800 iar pe celelalte pe 1900...
    companion object Constants {
        const val SUPRAVEGHETOR_PORT = 1800
        const val SUPRAVEGHETOR_HOST = "localhost"
        const val WAIT : Long = 150
    }

    init {

        serverSupraveghetor = ServerSocket(SUPRAVEGHETOR_PORT)
        println("Supraveghetorul se executa pe portul: ${serverSupraveghetor.localPort}")
        println("Se asteapta mesaje cu starea celorlalte microservicii")
        /*creez obiectul Observable care va genera evenimente pentru tratarea mesajelor*/
        receiveMessageObservable = Observable.create<String>
        {
            emitter ->
            while(true)
            {
                //tratez pentru fiecare tip de mesaj pe care trebuie sa il primesc separat
                messageProcessorSocket = acceptSocket()
                //TODO de gasit o metoda sa le opresc cand asteapta prea mult
                //prelucrez mesajul primit
                var mesaj = readMessage(messageProcessorSocket)
                if(mesaj == "0")
                {
                    emitter.onComplete()
                    messageProcessorSocket.close()
                }
                else{
                    emitter.onNext(mesaj)
                }

                biddingProcessSocket = acceptSocket()
                //TODO de gasit o metoda sa le opresc cand asteapta prea mult
                //prelucrez mesajul primit
                 mesaj = readMessage(biddingProcessSocket)
                if(mesaj == "0")
                {
                    emitter.onComplete()
                    biddingProcessSocket.close()
                }
                else{
                    emitter.onNext(mesaj)
                }


                auctioneerSocket = acceptSocket()
                //TODO de gasit o metoda sa le opresc cand asteapta prea mult
                //prelucrez mesajul primit
                mesaj = readMessage( auctioneerSocket)
                if(mesaj == "0")
                {
                    emitter.onComplete()
                    auctioneerSocket.close()
                }
                else{
                    emitter.onNext(mesaj)
                }

                bidderSocket = acceptSocket()
                //TODO de gasit o metoda sa le opresc cand asteapta prea mult
                //prelucrez mesajul primit
                mesaj = readMessage(bidderSocket)
                if(mesaj == "0")
                {
                    emitter.onComplete()
                    bidderSocket.close()
                }
                else{
                    emitter.onNext(mesaj)
                }
            }
        }
    }
    fun readMessage(s:Socket):String
    {
        //creez bufferul din care voi citi datele
        val bufferReader = BufferedReader(InputStreamReader(s.inputStream))
        //citesc datele
        var message = bufferReader.readLine()
        if(message == null)
        {
           return "0"

        }
        return message

    }

    fun acceptSocket():Socket
    {
         var socket:Socket= serverSupraveghetor.accept()
        return socket
    }



    fun writeInFile()
    {
        //scriu in fisier datele din coada
        messageQueue.forEach {
            this.myFile.appendText("\n"+it)
        }
    }

    fun receiveProcess()
    {
        //primesc si adaug in coada mesajele
        val receiveInQueueSubscription = receiveMessageObservable
            .subscribeBy (
                onNext ={
                        val mesaj = Message.deserialize(it.toByteArray())
                    messageQueue.add(mesaj) },
            onComplete=
            {
                writeInFile()
            }
            )
            }




    fun run()
    {
        receiveProcess()
    }
}

fun main(args: Array<String>) {
    var supraveghetor = Supraveghetorul()
    supraveghetor.run()
}
