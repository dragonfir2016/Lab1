package com.example.simple_lab_example

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Task1Fragment(), "Завдання 1")
        adapter.addFragment(Task2Fragment(), "Завдання 2")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}

class Task1Fragment : androidx.fragment.app.Fragment(R.layout.fragment_task1) {

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputFields = arrayOf(
            view.findViewById<EditText>(R.id.inputH_p),
            view.findViewById<EditText>(R.id.inputC_p),
            view.findViewById<EditText>(R.id.inputS_p),
            view.findViewById<EditText>(R.id.inputN_p),
            view.findViewById<EditText>(R.id.inputO_p),
            view.findViewById<EditText>(R.id.inputW_p),
            view.findViewById<EditText>(R.id.inputA_p)
        )

        val calculateButton = view.findViewById<Button>(R.id.calculateButton)
        val resultsTextView = view.findViewById<TextView>(R.id.resultsTextView)

        calculateButton.setOnClickListener {
            try {
                val H_p = inputFields[0].text.toString().toDouble()
                val C_p = inputFields[1].text.toString().toDouble()
                val S_p = inputFields[2].text.toString().toDouble()
                val N_p = inputFields[3].text.toString().toDouble()
                val O_p = inputFields[4].text.toString().toDouble()
                val W_p = inputFields[5].text.toString().toDouble()
                val A_p = inputFields[6].text.toString().toDouble()

                val K_PC = 100 / (100 - W_p)
                val K_PT = 100 / (100 - W_p - A_p)

                val H_C = H_p * K_PC
                val C_C = C_p * K_PC
                val S_C = S_p * K_PC
                val N_C = N_p * K_PC
                val O_C = O_p * K_PC
                val A_C = A_p * K_PC

                val H_T = H_p * K_PT
                val C_T = C_p * K_PT
                val S_T = S_p * K_PT
                val N_T = N_p * K_PT
                val O_T = O_p * K_PT

                val Q_P_H = 339 * C_p + 1030 * H_p - 108.8 * (O_p - S_p) - 25 * W_p
                val Q_C_H = (Q_P_H + 0.025 * W_p) * (100 / (100 - W_p))
                val Q_T_H = (Q_P_H + 0.025 * W_p - A_p) * (100 / (100 - W_p - A_p))

                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.HALF_UP

                resultsTextView.text = """
                    Коефіцієнти переходу:
                    K_PC: ${df.format(K_PC)}
                    K_PT: ${df.format(K_PT)}

                    Склад сухої маси:
                    H_C: ${df.format(H_C)}
                    C_C: ${df.format(C_C)}
                    S_C: ${df.format(S_C)}
                    N_C: ${df.format(N_C)}
                    O_C: ${df.format(O_C)}
                    A_C: ${df.format(A_C)}

                    Склад горючої маси:
                    H_T: ${df.format(H_T)}
                    C_T: ${df.format(C_T)}
                    S_T: ${df.format(S_T)}
                    N_T: ${df.format(N_T)}
                    O_T: ${df.format(O_T)}

                    Нижча теплота згоряння:
                    Q_P_H: ${df.format(Q_P_H)} кДж/кг
                    Q_C_H: ${df.format(Q_C_H)} кДж/кг
                    Q_T_H: ${df.format(Q_T_H)} кДж/кг
                """.trimIndent()
            } catch (e: Exception) {
                resultsTextView.text = "Помилка вхідних даних. Перевірте введені значення."
            }
        }
    }
}

class Task2Fragment : androidx.fragment.app.Fragment(R.layout.fragment_task2) {

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputFields = arrayOf(
            view.findViewById<EditText>(R.id.inputH_g),
            view.findViewById<EditText>(R.id.inputC_g),
            view.findViewById<EditText>(R.id.inputS_g),
            view.findViewById<EditText>(R.id.inputO_g),
            view.findViewById<EditText>(R.id.inputA_g),
            view.findViewById<EditText>(R.id.inputW_p_mazut),
            view.findViewById<EditText>(R.id.inputQ_g)
        )

        val calculateButton = view.findViewById<Button>(R.id.calculateButton2)
        val resultsTextView = view.findViewById<TextView>(R.id.resultsTextView2)

        calculateButton.setOnClickListener {
            try {
                val H_g = inputFields[0].text.toString().toDouble()
                val C_g = inputFields[1].text.toString().toDouble()
                val S_g = inputFields[2].text.toString().toDouble()
                val O_g = inputFields[3].text.toString().toDouble()
                val A_g = inputFields[4].text.toString().toDouble()
                val W_p_mazut = inputFields[5].text.toString().toDouble()
                val Q_g = inputFields[6].text.toString().toDouble()

                val K_GC = 100 / (100 - W_p_mazut - A_g)

                val H_p = H_g * K_GC
                val C_p = C_g * K_GC
                val S_p = S_g * K_GC
                val O_p = O_g * K_GC
                val A_p = A_g * (100 / (100 - W_p_mazut))

                val Q_p = Q_g * (100 - W_p_mazut - A_g) / 100

                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.HALF_UP

                resultsTextView.text = """
                    Коефіцієнт переходу до робочої маси:
                    K_GC: ${df.format(K_GC)}

                    Склад робочої маси:
                    H_p: ${df.format(H_p)}
                    C_p: ${df.format(C_p)}
                    S_p: ${df.format(S_p)}
                    O_p: ${df.format(O_p)}
                    A_p: ${df.format(A_p)}

                    Нижча теплота згоряння:
                    Q_p: ${df.format(Q_p)} МДж/кг
                """.trimIndent()
            } catch (e: Exception) {
                resultsTextView.text = "Помилка вхідних даних. Перевірте введені значення."
            }
        }
    }
}

class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    private val fragmentList = mutableListOf<androidx.fragment.app.Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    override fun getItem(position: Int): androidx.fragment.app.Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = fragmentTitleList[position]

    fun addFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}
