/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author josimar
 */
import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private static final List<String> list;

    static {
        list = new ArrayList<>();
        list.add("Kiai Haji Abdul Halim");
        list.add("Jendral Besar Abdul Harris Nasution");
        list.add("Abdul Kadir");
        list.add("Abdul Muis");
        list.add("Marsekal Muda Abdulrachman Saleh");
        list.add("Kiai Haji Achmad Rifai");
        list.add("Prof. Mr. Achmad Subardjo");
        list.add("Haji Adam Malik");
        list.add("Mayor Jenderal Adenan Kapau Gani");
        list.add("Marsekal Muda Agustinus Adisucipto");
        list.add("Sultan Ageng Tirtayasa");
        list.add("Sultan Agung Hanyokrokusumo");
        list.add("Haji Agus Salim");
        list.add("Kiai Haji Ahmad Dahlan");
        list.add("Jenderal Ahmad Yani");
        list.add("Mgr. Albertus Sugiyapranata S.J.");
        list.add("Raja Ali Haji");
        list.add("Alimin");
        list.add("Tengku Amir Hamzah");
        list.add("Andi Abdullah Bau Massepe");
        list.add("Andi Jemma");
        list.add("Andi Mappanyukki");
        list.add("Haji Andi Sultan Daeng Raja");
        list.add("Pangeran Antasari");
        list.add("Arie Frederik Lasut");
        list.add("Raden Mas Tumenggung Ario Suryo");
        list.add("Bagindo Azizchan");
        //isi dengan data yang lain atau bisa juga dari database...
    }

    public static List<String> getData() {
        return list;

    }
}
