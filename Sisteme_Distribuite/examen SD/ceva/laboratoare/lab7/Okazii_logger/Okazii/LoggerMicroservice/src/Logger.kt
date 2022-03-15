import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import io.reactivex.rxjava3.core.Observable
class Logger {

    private var loggerSocket = ServerSocket() /*un server in care voi aduna informatii*/
    private var socketConnected = 0 /*numarul de conexiunii de pe server*/
    /*lock-urile folosite pentru sincronizare*/
    private var socketCounterLock :ReentrantLock = ReentrantLock()
    private var logLock : ReentrantLock = ReentrantLock()
    /*de unde primesc mesaje*/
    private var receiveMessageObservable: Observable<Socket>

    /*valorile constante pentru utilizarea aplicatiei*/
    companion object Constants {
        const val LOGGER_PORT = 2000
        const val FILE_PATH = "/home/ana/Desktop/examen SD/laboratoare/lab7/Okazii_logger/Okazii/log.txt"
        const val AUCTION_DURATION : Long = 30_000
    }

    init {
        val file = File(FILE_PATH) /*fisierul in care voi scrie*/
        if(!(file.exists())) {file.createNewFile() }/*verific daca exista sau nu*/
        println("Fisierul log este deschis in: " + FILE_PATH)

        loggerSocket = ServerSocket(LOGGER_PORT) //creez serverul
        loggerSocket.setSoTimeout(AUCTION_DURATION.toInt()) //timpul in care astept sa se conecteze
        logLock.lock() //obtin lock pentru a scrie in fisier
        Files.write(Paths.get(FILE_PATH), ("[" + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + "] Fisierul log este deschis in: $FILE_PATH." + "\n").toByteArray(), StandardOpenOption.APPEND)
        Files.write(Paths.get(FILE_PATH), ("[" + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + "] LoggingMicroservice se executa pe portul: ${loggerSocket.localPort}" + "\n").toByteArray(), StandardOpenOption.APPEND)
        Files.write(Paths.get(FILE_PATH), ("[" + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + "] Se asteapta mesaje pentru scriere in log...\n").toByteArray(), StandardOpenOption.APPEND)
        logLock.unlock()

        println("LoggingMicroservice se executa pe portul: ${loggerSocket.localPort}")
        println("Se asteapta mesaje pentru scriere in log...")
        Thread.sleep(5_000)
        receiveMessageObservable = Observable.create<Socket> {
            /*astept conexiunii sa fie trimise*/
            try {
                while(true)
                {
                    val senderSocket = loggerSocket.accept()
                    thread { startConnection(senderSocket) }
                    //pornesc un thread prelucreze functia
                    socketCounterLock.lock()
                    socketConnected++
                    socketCounterLock.unlock()

                }
            }
            catch (e:SocketException)
            {
                logLock.lock()
                Files.write(
                    Paths.get(FILE_PATH),
                    ("[" + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + "] LoggerClosed \n\n").toByteArray(),
                    StandardOpenOption.APPEND
                )
                logLock.unlock()
            }
        }
        println("Am iesit din logger")

    }
    fun startConnection(senderSocket:Socket)
    {
        try {
            //citesc din buffer si pun in fisier, afisez la consola
            val bufferReader = BufferedReader(InputStreamReader(senderSocket.inputStream))
            while (true) {
                var receivedMessage = bufferReader.readLine()
                println("Mesajul primit este: $receivedMessage")

                if (receivedMessage == null) {
                    bufferReader.close()
                    break
                }

                //if (Message.deserialize(receivedMessage.toByteArray()).body == "stop") {
                receivedMessage = "[" + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date(receivedMessage.split(' ')[0].toLong())) + "]" + receivedMessage.replaceBefore(" ", "")

                logLock.lock()
                Files.write(Paths.get(FILE_PATH), (receivedMessage + "\n").toByteArray(), StandardOpenOption.APPEND)
                logLock.unlock()

            }
        }
        catch (ex : Exception)
        {
            logLock.lock()
            Files.write(Paths.get(FILE_PATH), (SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + " Exceptie in log: " + ex.stackTrace + "\n\n").toByteArray(), StandardOpenOption.APPEND)
            logLock.unlock()
        }
        finally {
            //eliberez resursele folosite, eliberez lock-urile si inchid conexiunea pe
                //socket
            if(logLock.isLocked)
                logLock.unlock()

            if(socketCounterLock.isLocked)
                socketCounterLock.unlock()

            senderSocket.close()
        }

        socketCounterLock.lock()
        socketConnected--
        socketCounterLock.unlock()

        println("Ies din thread. Thread-uri ramase: $socketConnected")

        if(socketConnected == 0)
            this.loggerSocket.close()

    }
    }
fun main(args: Array<String>) {
    val loggingMicroservice = Logger()
}