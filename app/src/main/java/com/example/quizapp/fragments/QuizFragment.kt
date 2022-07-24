package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    data class Question(
        val text: String,
        val answers: List<String>
    )

    private val questions = mutableListOf<Question>(
        Question(
            text = "Which one of these is an Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "What do you use to mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<dataBinding>", "<dBinding>")
        ),
        Question(
            text = "Which XML element lets you register an activity with the launcher activity?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "What is the base class for Layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "Tools", "Documentation", "Libraries")
        )

    )

    lateinit var currQues : Question
    lateinit var ans : MutableList<String>
    private var quesIdx = 0
    private val totalQues = 3


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentQuizBinding>(inflater, R.layout.fragment_quiz, container, false)

        binding.questions = this

        randomizeQuestions()
        binding.submit.setOnClickListener {
            val checkId = binding.radioGroup.checkedRadioButtonId
            if(checkId!=-1){

                var answerIdx = when(checkId){
                    R.id.answerSecond->1
                    R.id.answerThird->2
                    R.id.answerFourth->3
                    else ->0
                }

                if(ans[answerIdx]==currQues.answers[0]){
                    quesIdx++
                    if(quesIdx<totalQues){
                        currQues = questions[quesIdx]
                        setQuestion()
                        binding.invalidateAll()

                    }else{
                        it.findNavController().navigate(R.id.action_quizFragment_to_quizWinFragment)
                    }

                }else{
                    it.findNavController().navigate(R.id.action_quizFragment_to_quizOverFragment)
                }
            }

        }


        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        quesIdx = 0
        setQuestion()
    }

    private fun setQuestion() {
        currQues = questions[quesIdx]
        ans = currQues.answers.toMutableList()
        ans.shuffle()

    }
}