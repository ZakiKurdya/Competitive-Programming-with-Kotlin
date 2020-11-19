import java.util.*
fun main(args: Array<String>) {
    val inP = Scanner(System.`in`)
    val T = inP.nextInt()
    var Xi: Double
    var Yi: Int
    var count = 0
    var slopeY:Int
    for (TC in 1..T) {
        val n = inP.nextInt()
        val h = inP.nextDouble()
        val a = inP.nextDouble()
        val aM = a * -1
        for (i in 0 until n) {
            Xi = inP.nextDouble()
            Yi = inP.nextInt()
            if (Xi in aM..a && h >= Yi) {
                if (Xi > 0){
                    slopeY = ((Xi*(h/-a))+h).toInt()
                    if (Yi <= slopeY){
                        count ++
                    }
                }else{
                    slopeY = ((Xi*(h/-aM))+h).toInt()
                    if (Yi <= slopeY){
                        count ++
                    }
                }
            }
        }
        println(count)
    }
}

// BY DEV.ZAKI