package genesis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Vue {
    String nomProject;

    
    public Vue(String nomProject){
        this.nomProject = nomProject;
    }

    public String getNomProject() {
        return nomProject;
    }


    public void setNomProject(String nomProject) {
        this.nomProject = nomProject;
    }


    
    public void createProjetVue(){
        String commande = "ng new "+this.getNomProject()+" --style=css --skip-install";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            
            processBuilder.command("cmd.exe", "/c", commande);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
            }

            int codeRetour = process.waitFor();
            System.out.println("La commande s'est terminée avec le code de sortie : " + codeRetour);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
