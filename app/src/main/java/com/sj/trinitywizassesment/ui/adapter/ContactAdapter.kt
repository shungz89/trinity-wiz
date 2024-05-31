import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sj.trinitywizassesment.R
import com.sj.trinitywizassesment.model.Contact

class ContactAdapter(private val context: Context, private var contacts: List<Contact>) : BaseAdapter() {

    override fun getCount(): Int {
        return contacts.size
    }

    override fun getItem(position: Int): Contact {
        return contacts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact_layout, parent, false)
            holder = ViewHolder()
            holder.itemInitialsTextView = view.findViewById(R.id.itemInitialsTextView)
            holder.nameTextView = view.findViewById(R.id.itemTextView)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val contact = getItem(position)
        holder.itemInitialsTextView.text = contact.initials
        holder.nameTextView.text = "${contact.firstName} ${contact.lastName ?: ""}"

        return view
    }

    fun updateData(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    private class ViewHolder {
        lateinit var itemInitialsTextView: TextView
        lateinit var nameTextView: TextView
    }
}
