package genesis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import handyman.HandyManUtils;

public class Entity {
    private String tableName;
    private EntityColumn[] columns;
    private String className;
    private EntityField[] fields;
    private EntityField primaryField;
    public EntityField getPrimaryField() {
        return primaryField;
    }
    public void setPrimaryField(EntityField primaryField) {
        this.primaryField = primaryField;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public EntityColumn[] getColumns() {
        return columns;
    }
    public void setColumns(EntityColumn[] columns) {
        this.columns = columns;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public EntityField[] getFields() {
        return fields;
    }
    public void setFields(EntityField[] fields) {
        this.fields = fields;
    }
    public void initialize(Connection connex, Credentials credentials, Database database, Language language,ViewConfig viewConfig) throws ClassNotFoundException, SQLException, Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            connect=database.getConnexion(credentials);
            opened=true;
        }
        String query=database.getGetcolumnsQuery().replace("[tableName]", getTableName());
        PreparedStatement statement=connect.prepareStatement(query);
        try{
            Vector<EntityColumn> listeCols=new Vector<>();
            Vector<EntityField> listeFields=new Vector<>();
            EntityColumn column;
            EntityField field;
            try(ResultSet result=statement.executeQuery()){
                setClassName(HandyManUtils.majStart(HandyManUtils.toCamelCase(getTableName())));
                while(result.next()){
                    column=new EntityColumn();
                    column.setName(result.getString("column_name"));
                    column.setType(result.getString("data_type"));
                    column.setPrimary(result.getBoolean("is_primary"));
                    column.setForeign(result.getBoolean("is_foreign"));
                    column.setReferencedTable(result.getString("foreign_table_name"));
                    column.setReferencedColumn(result.getString("foreign_column_name"));
                    field=new EntityField();
                    if(column.isForeign()){
                        field.setName(HandyManUtils.minStart(HandyManUtils.toCamelCase(column.getReferencedTable())));
                        field.setType(HandyManUtils.majStart(HandyManUtils.toCamelCase(column.getReferencedTable())));
                        field.setReferencedField(HandyManUtils.toCamelCase(column.getReferencedColumn()));
                    }else{
                        field.setName(HandyManUtils.toCamelCase(column.getName()));
                        field.setType(language.getTypes().get(database.getTypes().get(column.getType())));
                        field.setReferencedView(viewConfig.getTypes().get(field.getType()));
                        field.setReferencedInput(viewConfig.getTypesInput().get(field.getType()));
                    }
                    field.setPrimary(column.isPrimary());
                    field.setForeign(column.isForeign());
                    if(field.isPrimary()){
                        setPrimaryField(field);
                    }
                    listeCols.add(column);
                    listeFields.add(field);
                }
                EntityColumn[] cols=new EntityColumn[listeCols.size()];
                for(int i=0;i<cols.length;i++){
                    cols[i]=listeCols.get(i);
                }
                EntityField[] fiels=new EntityField[listeFields.size()];
                for(int i=0;i<fiels.length;i++){
                    fiels[i]=listeFields.get(i);
                }
                setColumns(cols);
                setFields(fiels);
            }
        }finally{
            statement.close();
            if(opened){
                connect.close();
            }
        }
    }



    public void createDossier(String nameProjectVue){
        File dossierAdd = new File(nameProjectVue + "/" + "src/app/add-"+this.getTableName());
        File dossierListe = new File(nameProjectVue + "/" + "src/app/liste-"+this.getTableName());
        File dossierUpdate = new File(nameProjectVue + "/" + "src/app/update-"+this.getTableName());
        File dossierModel = new File(nameProjectVue + "/" + "src/app/model");
        File dossierService = new File(nameProjectVue + "/" + "src/app/service");
        dossierAdd.mkdir();
        dossierListe.mkdir();
        dossierUpdate.mkdir();
        dossierModel.mkdir();
        dossierService.mkdir();
    }

    public void dupliquerFichier(String fichierSource,String nouveauNom){
        try {
            Path cheminFichierSource = Paths.get(fichierSource);
            Path cheminNouveauFichier = cheminFichierSource.resolveSibling(nouveauNom);
            Files.copy(cheminFichierSource, cheminNouveauFichier);
        } catch (IOException e) {
        }
    }


    public void createFichier(){
        String fichierAdd_component = "data_genesis/vue/add/component.templ";
        this.dupliquerFichier(fichierAdd_component,"add-"+this.getTableName()+".component.ts");
        String fichierAdd_html = "data_genesis/vue/add/html.templ";
        this.dupliquerFichier(fichierAdd_html,"add-"+this.getTableName()+".component.html");
        String fichierAdd_css = "data_genesis/vue/add/style.templ";
        this.dupliquerFichier(fichierAdd_css,"add-"+this.getTableName()+".component.css");

        String fichierListe_component = "data_genesis/vue/liste/component.templ";
        this.dupliquerFichier(fichierListe_component,"liste-"+this.getTableName()+".component.ts");
        String fichierListe_html = "data_genesis/vue/liste/html.templ";
        this.dupliquerFichier(fichierListe_html,"liste-"+this.getTableName()+".component.html");
        String fichierListe_css = "data_genesis/vue/liste/style.templ";
        this.dupliquerFichier(fichierListe_css,"liste-"+this.getTableName()+".component.css");

        String fichierUpdate_component = "data_genesis/vue/update/component.templ";
        this.dupliquerFichier(fichierUpdate_component,"update-"+this.getTableName()+".component.ts");
        String fichierUpdate_html = "data_genesis/vue/update/html.templ";
        this.dupliquerFichier(fichierUpdate_html,"update-"+this.getTableName()+".component.html");
        String fichierUpdate_css = "data_genesis/vue/update/style.templ";
        this.dupliquerFichier(fichierUpdate_css,"update-"+this.getTableName()+".component.css");
    
        String fichierModele = "data_genesis/vue/modele/model.templ";
        this.dupliquerFichier(fichierModele, this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1)+"Model.ts");
        
        String fichierService = "data_genesis/vue/service/service.templ";
        this.dupliquerFichier(fichierService, this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1)+"Service.ts");
        
        String fichierRoute = "data_genesis/vue/route/route.templ";
        this.dupliquerFichier(fichierRoute, "app.routes.ts");
    }

    
    public String validator(){
        String validator = "";
        EntityColumn[] liste_colonne = this.getColumns();
        for (EntityColumn entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary())
                validator = validator + entityColumn.getName()+": ['', Validators.required], \n";
        }
        return validator;
    }


    public EntityField getComlumnPrimNonPrimary(){
        EntityField[] liste_colonne = this.getFields();
        EntityField field = new EntityField();
        for (EntityField entityField : liste_colonne) {
            if(!entityField.isPrimary()){
                field =  entityField;
            }
        }
        return field;
    }


    public String constructInput(Entity[] entities){
        String input = "";
        EntityField[] liste_colonne = this.getFields();
        EntityColumn[] liste_colonne_base = this.getColumns();
        int i=0;
        for (EntityField entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary() && !entityColumn.isForeign())
            {
                input = input + "<div class=\"mb-3\">\r\n" + //
                        "    <label for=\""+entityColumn.getName()+"\" class=\"form-label\">"+entityColumn.getName()+"</label>\r\n" + //
                        "    <input type=\""+entityColumn.getReferencedInput()+"\" class=\"form-control\" id=\""+entityColumn.getName()+"\" formControlName=\""+liste_colonne_base[i].getName()+"\">\r\n" + //
                        "  </div> \n";
            }else if(entityColumn.isForeign()){
                Entity foreign = entityColumn.getEntityForeignKey(entities);
                EntityField primary = foreign.getPrimaryField();
                String name =foreign.getTableName().substring(0, 1).toUpperCase()+foreign.getTableName().substring(1);
                EntityField fieldforeign = foreign.getComlumnPrimNonPrimary();
                input = input + "<div class=\"mb-3\">\r\n <label for=\"page\" class=\"form-label\">"+foreign.getTableName()+"</label> \n" + //
                        "<select class=\"form-select\" aria-label=\"Default select example\" formControlName=\""+liste_colonne_base[i].getName()+"\">\r\n" + //
                        "  <option  *ngFor=\"let "+foreign.getTableName()+" of liste"+name+" | async\" value=\"{{"+foreign.getTableName()+"."+primary.getName()+"}}\"> {{"+foreign.getTableName()+"."+fieldforeign.getName()+"}}</option>\r\n" + //
                        "</select>   </div> \r\n" + //
                                "";
            }
            i++;
        }
        return input;
    }

    public String dataDeclaration(){
        EntityColumn[] liste_colonne = this.getColumns();
        String reponse = "";
        for (EntityColumn entityField : liste_colonne) {
            reponse = reponse + "const "+entityField.getName()+" = this."+this.getTableName()+"Form.get('"+entityField.getName()+"')?.value; \n";
        }
        return reponse;
    }

    public String dataDeclarationArgument(){
        EntityColumn[] liste_colonne = this.getColumns();
        String reponse = "";
        int taille = liste_colonne.length;
        int i=0;
        for (EntityColumn entityField : liste_colonne) {
            i++;
            if(!entityField.isPrimary()){
                reponse = reponse + entityField.getName();
                if(i!=taille){
                    reponse= reponse+",";
                }
            }
        }
        return reponse;
    }



    public void remplaceFichierAdd(String nomProjet,Entity[] entities) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/add/add-"+this.getTableName()+".component.ts");
        List<String> lines = Files.readAllLines(chemin);
        String dataDeclarationArgument = this.dataDeclaration();
        for (int i=0;i<lines.size();i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            ligne = ligne.replace("[validator]", this.validator());
            ligne = ligne.replace("[dataDeclaration]", dataDeclarationArgument);
            ligne = ligne.replace("[dataDeclarationArgument]", this.dataDeclarationArgument());
            lines.set(i, ligne+"");
        }

        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        Path cheminHtml = Paths.get("data_genesis/vue/add/add-"+this.getTableName()+".component.html");
        List<String> linesHtml = Files.readAllLines(cheminHtml);
        
        for (int i = 0; i < linesHtml.size(); i++) {
            String ligne = linesHtml.get(i);
            ligne = ligne.replace("[input]", this.constructInput(entities));
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            linesHtml.set(i, ligne+"");
        }
        Files.write(cheminHtml, linesHtml, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app/add-"+this.getTableName());
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
        Files.move(cheminHtml, cheminDossierDestinationComponent.resolve(cheminHtml.getFileName()));
        Path cheminCss = Paths.get("data_genesis/vue/add/add-"+this.getTableName()+".component.css");
        Files.move(cheminCss, cheminDossierDestinationComponent.resolve(cheminCss.getFileName()));
    }

    public String constructTeteListe(){
        String tete = "";
        EntityField[] liste_colonne = this.getFields();
        for (EntityField entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary())
            {
                tete = tete + "<th scope=\"col\">"+entityColumn.getName()+"</th> \n";
            }
        }
        return tete;
    }


    public String constructBodyListe(Entity[] entities){
        String body = "";
        EntityField[] liste_colonne = this.getFields();

        for (EntityField entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary() && !entityColumn.isForeign())
            {
                body = body + "<td>{{ "+this.getTableName()+"."+entityColumn.getName()+" }}</td> \n";
            }else if(entityColumn.isForeign()){
                Entity foreign = entityColumn.getEntityForeignKey(entities);
                body = body + "<td>{{ "+this.getTableName()+"."+entityColumn.getName()+"."+foreign.getComlumnPrimNonPrimary().getName()+" }}</td> \n";
            }
        }
        return body;
    }



    public void remplaceFichierListe(String nomProjet,Entity[] entities) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/liste/liste-"+this.getTableName()+".component.ts");
        List<String> lines = Files.readAllLines(chemin);
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    
        Path cheminHtml = Paths.get("data_genesis/vue/liste/liste-"+this.getTableName()+".component.html");
        List<String> linesHtml = Files.readAllLines(cheminHtml);

        for (int i = 0; i < linesHtml.size(); i++) {
            String ligne = linesHtml.get(i);
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            ligne = ligne.replace("[tete]", this.constructTeteListe());
            ligne = ligne.replace("[body]", this.constructBodyListe(entities));
            ligne = ligne.replace("[id]", this.getPrimaryField().getName());
            linesHtml.set(i, ligne+"");
        }
        Files.write(cheminHtml, linesHtml, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app/liste-"+this.getTableName());
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
        Files.move(cheminHtml, cheminDossierDestinationComponent.resolve(cheminHtml.getFileName()));
        Path cheminCss = Paths.get("data_genesis/vue/liste/liste-"+this.getTableName()+".component.css");
        Files.move(cheminCss, cheminDossierDestinationComponent.resolve(cheminCss.getFileName()));
    }

    public String constructAttributModele(Entity[] entities){
        String attribut = "";
        EntityField[] liste_colonne = this.getFields();
        EntityColumn[] liste_colonne_base = this.getColumns();
        int i =0;
        for (EntityField entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary() && !entityColumn.isForeign())
            {
                attribut = attribut + liste_colonne_base[i].getName()+ ":"+entityColumn.getReferencedView()+"; \n";
            }else if(entityColumn.isForeign()){
                attribut = attribut + entityColumn.getName()+":"+entityColumn.getType()+"Model;\n";
            }
            i++;
        }

        if(this.getPrimaryField().getType().equals("Integer")){
            attribut = attribut + this.getPrimaryField().getName()+"?:number \n";
        }else if(this.getPrimaryField().getType().equals("String")){
            attribut = attribut + this.getPrimaryField().getName()+"?:string \n";
        }
        return attribut;
    }

    public String constructConstructor(Entity[] entities){
        String constructor = "";
        EntityField[] liste_colonne = this.getFields();
        EntityColumn[] liste_colonne_base = this.getColumns();
        String entre = "";
        int i = 0;
        String initialisation = "";
        for (EntityField entityColumn : liste_colonne) {
            if(!entityColumn.isPrimary() && !entityColumn.isForeign())
            {   
                entre = entre + liste_colonne_base[i].getName()+":"+entityColumn.getReferencedView();
                if(i!=(liste_colonne.length-1)){
                    entre =  entre + ",";
                }
                initialisation = initialisation + "this."+liste_colonne_base[i].getName()+"="+liste_colonne_base[i].getName()+";\n";
            }else if(entityColumn.isForeign()){
                entre = entre + entityColumn.getName()+":"+entityColumn.getType()+"Model";
                if(i!=(liste_colonne.length-1)){
                    entre =  entre + ",";
                }
                initialisation = initialisation + "this."+entityColumn.getName()+"="+entityColumn.getName()+";\n";
            }
            i++;
        }

        if(this.getPrimaryField().getType().equals("Integer")){
            entre = entre + ","+this.getPrimaryField().getName()+"?:number";
            initialisation = initialisation + "this."+this.getPrimaryField().getName()+"="+this.getPrimaryField().getName()+";\n";
        }else if(this.getPrimaryField().getType().equals("String")){
            entre = entre + ","+this.getPrimaryField().getName()+"?:string";
            initialisation = initialisation + "this."+this.getPrimaryField().getName()+"="+this.getPrimaryField().getName()+";\n";
        }

        constructor = constructor + "constructor("+entre+"){ \n"+initialisation+"\n } \n";

        return constructor;
    }

    public String importationModel(){
        String importation = "";
        EntityField[] liste_colonne = this.getFields();
        for (EntityField entityField : liste_colonne) {
            if (entityField.isForeign()) {
                importation = "import { "+entityField.getType()+"Model } from \"./"+entityField.getType()+"Model\"\n";
            }
        }
        return importation;
    }

    public void remplacerFichierModele(String nomProjet,Entity[] entities) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/modele/"+this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1)+"Model.ts");
        List<String> lines = Files.readAllLines(chemin);
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[attribut]", this.constructAttributModele(entities));
            ligne = ligne.replace("[constructor]", this.constructConstructor(entities));
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            ligne = ligne.replace("[importation]", this.importationModel());
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app/model");
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
    } 

    public String argumentCreate(){
        String reponse = "";
        EntityField[] liste_colonne = this.getFields();
        int taille = liste_colonne.length;
        int i=0;
        for (EntityField entityField : liste_colonne) {
            i++;
            if(!entityField.isPrimary()){
                reponse = reponse + entityField.getName()+": any";
                if(i!=taille){
                    reponse = reponse+",";
                }
            }
        }
        return reponse;
    }

    public String argumentUpdate(){
        String reponse = "";
        EntityField[] liste_colonne = this.getFields();
        int taille = liste_colonne.length;
        int i=0;
        for (EntityField entityField : liste_colonne) {
            reponse = reponse + entityField.getName()+": any";
            i++;
            if(i!=taille){
                reponse = reponse+",";
            }
        }
        return reponse;
    }

    public String requestParamUpdate(){
        String reponse = "";
        EntityField[] liste_colonne = this.getFields();
        int taille = liste_colonne.length;
        int i=0;
        for (EntityField entityField : liste_colonne) {
            reponse = reponse + entityField.getName()+"=${"+entityField.getName()+"}";
            i++;
            if(i!=taille){
                reponse = reponse+"&";
            }
        }
        return reponse;
    }


    public String requestParamCreate(){
        String reponse = "";
        EntityField[] liste_colonne = this.getFields();
        int taille = liste_colonne.length;
        int i=0;
        for (EntityField entityField : liste_colonne) {
            if(!entityField.isPrimary()){

            reponse = reponse + entityField.getName()+"=${"+entityField.getName()+"}";
            i++;
            if(i!=(taille-1)){
                reponse = reponse+"&";
            }
        }
        }
        return reponse;
    }


    public void remplacerFichierService(String nomProjet) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/service/"+this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1)+"Service.ts");
        List<String> lines = Files.readAllLines(chemin);
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            ligne = ligne.replace("[baseUrl]", "'http://localhost:8080'");
            ligne = ligne.replace("[id]", this.getPrimaryField().getName());
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            ligne = ligne.replace("[argumentUpdate]", this.argumentUpdate());
            ligne = ligne.replace("[requestParamUpdate]", this.requestParamUpdate());
            ligne = ligne.replace("[argumentCreate]", this.argumentCreate());
            ligne = ligne.replace("[requestParamCreate]", this.requestParamCreate());
              lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app/service");
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
    }

    public void remplacerFichierUpdate(String nomProjet,Entity[] entities) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/update/update-"+this.getTableName()+".component.ts");
        List<String> lines = Files.readAllLines(chemin);
        EntityField primary = this.getPrimaryField();
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[GnomClasse]", this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1));
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            ligne = ligne.replace("[validator]", this.validator());
            ligne = ligne.replace("[dataDeclaration]", this.dataDeclaration());
            ligne = ligne.replace("[dataDeclarationArgument]", this.dataDeclarationArgument());

            if(primary.getType().equals("Integer")){
                ligne = ligne.replace("[signNumber]", "+");
            }
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminHtml = Paths.get("data_genesis/vue/update/update-"+this.getTableName()+".component.html");
        List<String> linesHtml = Files.readAllLines(cheminHtml);
        for (int i = 0; i < linesHtml.size(); i++) {
            String ligne = linesHtml.get(i);
            ligne = ligne.replace("[input]", this.constructInput(entities));
            ligne = ligne.replace("[nomClasse]", this.getTableName());
            linesHtml.set(i, ligne+"");
        }
        Files.write(cheminHtml, linesHtml, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app/update-"+this.getTableName());
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
        Files.move(cheminHtml, cheminDossierDestinationComponent.resolve(cheminHtml.getFileName()));
        Path cheminCss = Paths.get("data_genesis/vue/update/update-"+this.getTableName()+".component.css");
        Files.move(cheminCss, cheminDossierDestinationComponent.resolve(cheminCss.getFileName()));
    }


    public void verificationForeignAdd(Entity[] entities) throws IOException{
        EntityField[] liste_colonne = this.getFields();
        String importForeign = "";
        String attributForeign = "";
        String fonctionForeign = "";
        String constructorForeign = "";
        String appelFonctionForeign = "";
        for (EntityField entityField : liste_colonne) {
            if(entityField.isForeign()){
                Entity foreign = entityField.getEntityForeignKey(entities);
                String name =foreign.getTableName().substring(0, 1).toUpperCase()+foreign.getTableName().substring(1);
                importForeign = importForeign + "import { "+name+"Service } from '../service/"+name+"Service'; \n";
                importForeign = importForeign + "import { "+name+"Model } from '../model/"+name+"Model'; \n";
                attributForeign = attributForeign+"liste"+name+"!: Observable<"+name+"Model[]>; \n";
                fonctionForeign = fonctionForeign + "getAll"+name+"() {\r\n" + //
                        "      this.liste"+name+" = this."+foreign.getTableName()+"Service.getAll(); \r\n" + //
                        "    }";
                constructorForeign = ",private "+foreign.getTableName()+"Service: "+name+"Service";
                appelFonctionForeign = "this.getAll"+name+"();";
            }
        }

        Path chemin = Paths.get("data_genesis/vue/add/add-"+this.getTableName()+".component.ts");

        List<String> lines = Files.readAllLines(chemin);
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[importForeign]", importForeign);
            ligne = ligne.replace("[attributForeign]", attributForeign);
            ligne = ligne.replace("[fonctionForeign]", fonctionForeign);
            ligne = ligne.replace("[constructorForeign]", constructorForeign);
            ligne = ligne.replace("[appelFonctionForeign]", appelFonctionForeign);
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void verificationForeignUpdate(Entity[] entities) throws IOException{
        EntityField[] liste_colonne = this.getFields();
        String importForeign = "";
        String attributForeign = "";
        String fonctionForeign = "";
        String constructorForeign = "";
        String appelFonctionForeign = "";
        for (EntityField entityField : liste_colonne) {
            if(entityField.isForeign()){
                Entity foreign = entityField.getEntityForeignKey(entities);
                String name =foreign.getTableName().substring(0, 1).toUpperCase()+foreign.getTableName().substring(1);
                importForeign = importForeign + "import { "+name+"Service } from '../service/"+name+"Service'; \n";
                importForeign = importForeign + "import { "+name+"Model } from '../model/"+name+"Model'; \n";
                attributForeign = attributForeign+"liste"+name+"!: Observable<"+name+"Model[]>; \n";
                fonctionForeign = fonctionForeign + "getAll"+name+"() {\r\n" + //
                        "      this.liste"+name+" = this."+foreign.getTableName()+"Service.getAll(); \r\n" + //
                        "    }";
                constructorForeign = ",private "+foreign.getTableName()+"Service: "+name+"Service";
                appelFonctionForeign = "this.getAll"+name+"();";
            }
        }

        Path chemin = Paths.get("data_genesis/vue/update/update-"+this.getTableName()+".component.ts");

        List<String> lines = Files.readAllLines(chemin);
        for (int i = 0; i < lines.size(); i++) {
            String ligne = lines.get(i);
            ligne = ligne.replace("[importForeign]", importForeign);
            ligne = ligne.replace("[attributForeign]", attributForeign);
            ligne = ligne.replace("[fonctionForeign]", fonctionForeign);
            ligne = ligne.replace("[constructorForeign]", constructorForeign);
            ligne = ligne.replace("[appelFonctionForeign]", appelFonctionForeign);
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    public String importRoute(){
        String reponse = "";

        String name =this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1);

            reponse = reponse + "import { Add"+name+"Component } from './add-"+this.getTableName()+"/add-"+this.getTableName()+".component'; \n"
            + "import { Liste"+name+"Component } from './liste-"+this.getTableName()+"/liste-"+this.getTableName()+".component'; \n"
            + "import { Update"+name+"Component } from './update-"+this.getTableName()+"/update-"+this.getTableName()+".component'; \n"
            ;
        return reponse;
    }

    public String pathRoute(){
        String reponse = "";
            String name =this.getTableName().substring(0, 1).toUpperCase()+this.getTableName().substring(1);

            reponse = reponse + "{'path':\"add"+name+"\",component:Add"+name+"Component}, \n"
            +"{'path':\"liste"+name+"\",component:Liste"+name+"Component}, \n"
            +"{'path':\"versUpdate"+name+"/:id\",component:Update"+name+"Component}, \n"
            ;
        return reponse;
    }

    public void remplaceFichierRoute(String nomProjet) throws IOException{
        Path chemin = Paths.get("data_genesis/vue/route/app.routes.ts");
        List<String> lines = Files.readAllLines(chemin);
        for(int i=0;i<lines.size();i++){
            String ligne = lines.get(i);
            ligne = ligne.replace("[importRoute]", this.importRoute());
            ligne = ligne.replace("[pathRoute]", this.pathRoute());
            lines.set(i, ligne+"");
        }
        Files.write(chemin, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        Path cheminDossierDestinationComponent = Paths.get(nomProjet+"/src/app");
        Files.move(chemin, cheminDossierDestinationComponent.resolve(chemin.getFileName()));
    }

    public void remplaceAllFichier(String nomProjet,Entity[] entities) throws IOException{
        this.verificationForeignAdd(entities);
        this.verificationForeignUpdate(entities);

        this.remplaceFichierAdd(nomProjet,entities);
        this.remplaceFichierListe(nomProjet,entities);
        this.remplacerFichierModele(nomProjet,entities);
        this.remplacerFichierService(nomProjet);
        this.remplacerFichierUpdate(nomProjet,entities);
    }
}
