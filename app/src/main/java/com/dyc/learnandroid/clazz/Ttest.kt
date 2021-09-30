package com.dyc.learnandroid.clazz

import kotlin.math.abs
import kotlin.math.cos

/**
 *@Author : yancheng
 *@Date : 2021/1/4
 *@Time : 14:01
 *@Describe ：
 **/




/**
 * 我们需要使用返回泛型的，那我们就使用out，如果我们需要将泛型放入方法中使用的，我们就是用in
 *
 * [out]表示子类泛型对象都可以赋值给父类泛型对象，大的包含小的，小的要到大的里面去是out，out只返回T，不消费T

[in] 表示父类泛型对象都可以赋值给子类泛型对象，大的包含小的，大的要进到小的里面去是in，in只消费T，不返回T

 */

interface Base{
    fun a()
}
class Banana

 class BananaFactory() : IProduction<Banana>{
    override fun produce(): Banana {
        return Banana()
    }

    companion object DefaultLibeler{


    }

}





fun foo() = object {
    val x: String = "x"
}




class openImp: Base {
    override fun a(){
        println("openImp")
    }
}

/**
 *
 * 有很多类都实现了或者继承了Base，具体DelegateBase要如何去实现Base的抽象方法，不单独定义，直接委托给b
 * 一般直接使用: Class by c来做委托实现，
 */
class DelegateBase (base: Base) : Base by base



val eps = 1E-10 // "good enough", could be 10^-15


/**
 * 尾递归函数  [tailrec]
 */
tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (abs(x - cos(x)) < eps) x else findFixPoint(cos(x))




tailrec fun againFun(x: Int) : Int = if( x < 0) x else againFun(x-1)

fun main() {

    val openImp = openImp()
    DelegateBase(openImp).a()

  println( findFixPoint())


    println(againFun(400))

    var a = 4
    val b = 5
    a *= b

    val list = mutableListOf(1,2,null,3)


    /**
     * 类引用对应的是MyClass::class -> KClass

    方法引用对应的是::myFun -> KFunction<out R>
     */
    println(list.filterNotNull().filter(::isOdd))


    val stringPlus: String.(String) -> String = String::plus


    excuseFun(3,success = {

    },error = {

    })

}

fun isOdd(x: Int) = x % 2 != 0


fun excuseFun(params :Int,success: () -> Unit,error: () -> Unit){
    if (dealFun(params)){
        success()
    }else{
        error()
    }
}


fun dealFun(param1 :Int)  :Boolean{
    return true

}




