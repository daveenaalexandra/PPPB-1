package com.example.mytablayout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mytablayout.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnregis.setOnClickListener {
                val intentToWelcomeActivity = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intentToWelcomeActivity)
            }
            txtAlrlog.setOnClickListener {
                val intentToLoginActivity = Intent(requireContext(), LoginFragment::class.java)
                startActivity(intentToLoginActivity)
            }
        }
    }
}
