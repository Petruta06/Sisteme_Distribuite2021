import java.lang.Exception
import java.nio.channels.AsynchronousByteChannel
import kotlin.random.Random

class Observabil {
    companion object{
        fun generateList(maxValue : Int) : List<Int>{
            var list : MutableList<Int> = mutableListOf()
            var contor : Int = maxValue
            var randomVal : Int = 0;
            while(contor > 0){
                randomVal = Random.nextInt(1,1000)
                if(randomVal > 999){
                    //throw Exception("Numarul $randomVal este prea mare!")
                    break
                }
                list.add(randomVal)
                contor = contor - 1;
                //println("S-a generat o valoare!")
            }
            return list
        }
    }
}