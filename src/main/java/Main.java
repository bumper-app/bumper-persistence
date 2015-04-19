
import com.bumper.persistence.dao.PeopleDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author math
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        PeopleDAO pdao = new PeopleDAO();
       
        
        System.out.println(pdao.findById(1));

    }

}
