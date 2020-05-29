package com.example.iwamotokaito2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.iwamotokaito2.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.Char as Char1

class MainActivity : AppCompatActivity() {

    //変数の定義
    //最後に押したボタンの数字を保存する変数
    var nStr: String = ""

    //浮動小数点の配列を定義
    // =ボタンが押されるまでに押された数字のボタンの数字を保存する配列
    val nList = ArrayList<Double>()
    // =ボタンが押されるまでに押された演算子のボタンの演算子を保存する配列
    val oList = ArrayList<Char1>()

    //計算タイプを切り替える数字 0->掛け算と割り算を優先して計算  1->順番に計算
    var change = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //0のボタンが押されたとき 表示してある文字に0を追加 , 変数であるnStrに0を代入
        //以下同様
        button0.setOnClickListener {
            textView.text = "${textView.text}0"
            nStr += "0"
        }
        button1.setOnClickListener {
            textView.text = "${textView.text}1"
            nStr += "1"
        }
        button2.setOnClickListener {
            textView.text = "${textView.text}2"
            nStr += "2"
        }
        button3.setOnClickListener {
            textView.text = "${textView.text}3"
            nStr += "3"
        }
        button4.setOnClickListener {
            textView.text = "${textView.text}4"
            nStr += "4"
        }
        button5.setOnClickListener {
            textView.text = "${textView.text}5"
            nStr += "5"
        }
        button6.setOnClickListener {
            textView.text = "${textView.text}6"
            nStr += "6"
        }
        button7.setOnClickListener {
            textView.text = "${textView.text}7"
            nStr += "7"
        }
        button8.setOnClickListener {
            textView.text = "${textView.text}8"
            nStr += "8"
        }
        button9.setOnClickListener {
            textView.text = "${textView.text}9"
            nStr += "9"
        }

        //カンマボタン
        //カンマボタンも同様に処理できる。
        buttonConma.setOnClickListener {
            textView.text = "${textView.text}."
            nStr += "."
        }

        // イコールボタン
        //addList()関数を使って最後に押された数字をnListに保存し、caluculate()関数で計算結果を変数であるresultに代入する。そして表示。
        //変数、配列を初期化
        buttonequal.setOnClickListener {
            textView.text = "${textView.text}="
            addList(nStr)
            var result = calculate().toString()
            textView.text = result
            nStr = result
            nList.clear()
            oList.clear()
        }

        //マイナスボタン
        //負である数字を計算させるために　　　・nStrが""、つまり最後に押されたボタンが演算子のボタンの時、nStrを負の数字にする。　・nStrに何か数字が入っているとき、つまり最後に押されたボタンが数字のボタンの時、ほかの演算子と同様に処理。
        buttonmainas.setOnClickListener {
            textView.text = "${textView.text}-"
            if (nStr == "") {
                nStr = "-" + nStr
            }else {
                addList(nStr, '-')
                nStr = ""
            }
        }

        //掛け算ボタン
        buttonmulti.setOnClickListener {
            textView.text = "${textView.text}×"
            addList(nStr, '*')
            nStr = ""

        }
        //足し算ボタン
        buttonplus.setOnClickListener {
            textView.text = "${textView.text}+"
            addList(nStr, '+')
            nStr = ""

        }
        //割り算ボタン
        buttonwaru.setOnClickListener {
            textView.text = "${textView.text}÷"
            addList(nStr, '/')
            nStr = ""

        }
        //リセットボタン(C)
        //表示されている文字、変数、配列を初期化
        buttonreset.setOnClickListener {
            textView.text = ""
            nStr = ""
            nList.clear()
            oList.clear()
        }
        //切替ボタン(計算の仕方のタイプを切り替えるボタン)
        //change = 0  -> 掛け算・割り算を優先して計算。　change = -0 -> 順次計算。
        //表示されている文字、変数、配列を初期化
        buttonchange.setOnClickListener {
            if (change == 0) {
                change = 1
                textView2.text = "順次計算タイプ"
            } else if (change == 1) {
                change = 0
                textView2.text = "掛け算・割り算を優先して計算する"
            }
            textView.text = ""
            nStr = ""
            nList.clear()
            oList.clear()
        }
    } //onCreate関数はここで終わり


    //関数

    //1.イコールボタンを押すまでに使うaddList関数
    //演算子ボタンが押されたときに呼び出し最後に押された数字、演算子それぞれをnList,oList配列に格納する(ただし数字はDouble型に変換する)
    // 演算子を2.3回連続で押されたとき計算できないためエラーをはかせる
    fun addList(str: String, enzansi: Char1) {
        try {
            var num = str.toDouble()
            nList.add(num)
            oList.add(enzansi)
        } catch (e: Exception) {
            textView.text = "エラー"
        }
    }
    //2.イコールボタンを押したときに使うaddList関数
    fun addList(str: String) {
        try {
            var num = str.toDouble()
            nList.add(num)
        } catch (e: Exception) {
            textView.text = "エラー"
        }
    }
    //計算する関数
    //計算タイプをif文で分けている。
    fun calculate(): Double {
        var result = 0.0

        var i = 0
        //掛け算割り算を優先して計算
        if (change == 0) {
            //掛け算・割り算だと先に計算して配列に戻す。引き算は数字を-1倍することで足し算に変える。これによって演算子をすべて足し算にしてあとでまとめて足す。
            while (i < oList.size) {
                if (oList[i] == '*') {
                    var result = nList[i] * nList[i + 1]
                    nList[i] = result
                    nList.removeAt(i + 1)
                    oList.removeAt(i)
                    i--
                } else if (oList[i] == '/') {

                    var result = nList[i] / nList[i + 1]
                    nList[i] = result
                    nList.removeAt(i + 1)
                    oList.removeAt(i)
                    i--
                } else if (oList[i] == '-') {
                    oList[i] = '+'
                    nList[i + 1] = nList[i+1] * -1

                } else if (oList[i] == '+') {

                }
                i++
            }
            //まとめて足す
            for (i in nList) {
                result += i
            }

        //順次計算
        } else if (change == 1) {
            result = nList[0]
            //順番に計算していくだけ
            while (i < oList.size) {
                if (oList[i] == '*') {
                    result *= nList[i + 1]
                } else if (oList[i] == '/') {
                    result /= nList[i + 1]
                } else if (oList[i] == '+') {
                    result += nList[i + 1]
                } else if (oList[i] == '-') {
                    result -= nList[i + 1]
                }
                i++
            }
        }
        return result


    }
}
