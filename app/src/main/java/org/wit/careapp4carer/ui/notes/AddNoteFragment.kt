package org.wit.careapp4carer.ui.notes

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import kotlinx.android.synthetic.main.listitem_notes.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotesModel
import org.wit.careapp4carer.models.firebase.NotesFireStore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddNote.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddNote.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNote : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private var notesList = NotesFireStore()
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        view.button_saveNote.setOnClickListener {
            val args: AddNoteArgs =
                AddNoteArgs.fromBundle(requireArguments())
            val selectedNote = args.note
            val updateTime = notesList.getDate()
            if (arguments?.get("note") == null || selectedNote?.id == "") {
                val title = note_add_title.text.toString()
                val content = note_content.text.toString()
                if (title == "" || content == "") {
                    val text = "Please fill all fields!"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), text, duration)
                    toast.show()
                } else {
                    val newNote = NotesModel("", title, content, updatedBy = "Carer", updatedDate = updateTime)
                    notesList.add(newNote)
                    it.findNavController().navigate(R.id.nav_notes)
                }
            } else {
                if (selectedNote != null) {
                    val title = note_add_title.text.toString()
                    val content = note_content.text.toString()

                    if (title == "" || content == "") {
                        val text = "Please fill all fields!"
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(requireContext(), text, duration)
                        toast.show()
                    } else {
                        val updatedNote = NotesModel(selectedNote.id, title, content, updatedBy = "Carer", updatedDate = updateTime)
                        notesList.edit(updatedNote)
                        it.findNavController().navigate(R.id.nav_notes)
                    }
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val args: AddNoteArgs =
                AddNoteArgs.fromBundle(requireArguments())
            val passedNote = args.note
            note_add_title.setText(passedNote?.title)
            note_content.setText(passedNote?.note)
            Log.d("msg", "$passedNote")
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNote.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNote().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
