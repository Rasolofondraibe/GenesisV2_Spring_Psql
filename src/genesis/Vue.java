package genesis;

import java.io.BufferedReader;
import java.io.File;
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
        File projet = new File(this.getNomProject());
        projet.mkdir();
        String commande = "xcopy data_genesis\\projectVue "+this.getNomProject()+" /s /i";
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
