import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.math.roundToInt

class Observator {
    companion object{
        fun run(){
//            val observable = Observable.fromIterable(Observabil.generateList(50))
            /*val observable = Observable.range(1,50)

            observable.subscribeBy(
                onNext = {
                    println("S-a generat numarul $it!")
                },
                onComplete = {
                    println("S-au generat toate numerele!")
                },
                onError = {
                    println("Eroare : S-a generat un numar prea mare!")
                }
            )

            observable.subscribe{
                val n = it.toDouble()
                val fibonnaci = ((Math.pow(1.61803,n) - Math.pow(0.61803, n)) / 2.23606).roundToInt()
                println(fibonnaci)
            }
*/
            val obs  = Observable.create<String>{
                emitter ->
                    Observabil.generateList(50).forEach{
                        emitter.onNext("$it!")
                        if(it > 900){
                            emitter.onError(RuntimeException("Numarul $it este prea mare!"))
                            return@create
                        }
                    }
                      emitter.onComplete()
            }
                val subscriber = obs.subscribeBy(
                onNext = {
                    println("S-a generat numarul $it!")
                },
                onComplete = {
                    println("S-au generat toate numerele!")
                },
                onError = {
                        println("Am intampinat o eroare! : $it")
                }
            )
            subscriber.dispose()

        }
    }
}

fun main(args: Array<String>){
    Observator.run()
}