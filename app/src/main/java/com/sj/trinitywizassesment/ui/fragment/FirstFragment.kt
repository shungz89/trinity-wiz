import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.sj.trinitywizassesment.MainActivity
import com.sj.trinitywizassesment.R
import com.sj.trinitywizassesment.model.Contact
import java.io.IOException
import java.nio.charset.Charset

class FirstFragment : Fragment() {

    private lateinit var gridView: GridView
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        gridView = view.findViewById(R.id.gridView)


        var contacts = (requireActivity() as MainActivity).getContacts()

//
        contactAdapter = ContactAdapter(requireContext(), contacts)
        gridView.adapter = contactAdapter

        return view
    }


    // Method to refresh the list of contacts
    fun refreshContacts(contacts: List<Contact>) {
        Log.d("FirstFragment", "refreshContacts: $contacts")
        contactAdapter.updateData(contacts)
    }

}
