package se.axel.bengtsson.kinapokerscorecalculator.ui.hand1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHand1Binding
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class Hand1Fragment : Fragment() {

    private var _binding: FragmentHand1Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(KinaPokerViewModel::class.java)

        _binding = FragmentHand1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
