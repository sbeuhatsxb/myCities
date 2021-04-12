package model;

import java.io.File;
import java.sql.*;

public class DataHandler {

    private static final String location  = "jdbc:sqlite:";
    private static final String filename  = "visitMyCities.db";
    private static final String url  = location + filename;


    public static void generatedDatabase() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void newDatabase() {
        File f = new File(filename);
        if(!f.exists() && !f.isDirectory()) {
            generatedDatabase();
            createTables();
        }
        generatedDatabase();
        createTables();
    }

    public static void connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createTables() {
        // SQLite connection string

        // SQL statement for creating a new table

        String createTable_role = "CREATE TABLE role(\n" +
                "\tid_role    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel      TEXT NOT NULL\n" +
                ");";

        String createTable_user = "CREATE TABLE user(\n" +
                "\tid_user     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlogin       TEXT NOT NULL ,\n" +
                "\tpassword    TEXT NOT NULL ,\n" +
                "\tid_role     INTEGER NOT NULL\n" +
                "\n" +
                "\t,CONSTRAINT user_role_FK FOREIGN KEY (id_role) REFERENCES role(id_role)\n" +
                ");";

        String createTable_architect = "CREATE TABLE architect(\n" +
                "\tid_architect    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel           TEXT NOT NULL\n" +
                ");";
        String createTable_type = "CREATE TABLE type(\n" +
                "\tid_type    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel      TEXT NOT NULL\n" +
                ");";
        String createTable_time = "CREATE TABLE time(\n" +
                "\tid_time    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel      TEXT NOT NULL\n" +
                ");\n";
        String createTable_style = "CREATE TABLE style(\n" +
                "\tid_style    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel       TEXT NOT NULL\n" +
                ");";
        String createTable_roof_type = "CREATE TABLE roof_type(\n" +
                "\tid_roof_type    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel           TEXT NOT NULL\n" +
                ");";
        String createTable_material = "CREATE TABLE material(\n" +
                "\tid_material    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel          TEXT NOT NULL\n" +
                ");";
        String createTable_country = "CREATE TABLE country(\n" +
                "\tid_country    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel         TEXT NOT NULL\n" +
                ");";
        String createTable_city = "CREATE TABLE city(\n" +
                "\tid_city       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel         TEXT NOT NULL ,\n" +
                "\tid_country    INTEGER NOT NULL\n" +
                "\n" +
                "\t,CONSTRAINT city_country_FK FOREIGN KEY (id_country) REFERENCES country(id_country)\n" +
                ");";
        String createTable_frame = "CREATE TABLE frame(\n" +
                "\tid_frame    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel       TEXT NOT NULL\n" +
                ");";

        String createTable_building = "CREATE TABLE building(\n" +
                "\tid_building     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tdescription     TEXT NOT NULL ,\n" +
                "\tyear            INTEGER NOT NULL ,\n" +
                "\twindows         INTEGER NOT NULL ,\n" +
                "\tid_city         INTEGER NOT NULL ,\n" +
                "\tid_architect    INTEGER ,\n" +
                "\tid_time         INTEGER ,\n" +
                "\tid_type         INTEGER ,\n" +
                "\tid_style        INTEGER ,\n" +
                "\tid_material     INTEGER ,\n" +
                "\tid_roof_type    INTEGER ,\n" +
                "\tid_frame        INTEGER\n" +
                "\n" +
                "\t,CONSTRAINT building_city_FK FOREIGN KEY (id_city) REFERENCES city(id_city)\n" +
                "\t,CONSTRAINT building_architect_FK FOREIGN KEY (id_architect) REFERENCES architect(id_architect)\n" +
                "\t,CONSTRAINT building_time_FK FOREIGN KEY (id_time) REFERENCES time(id_time)\n" +
                "\t,CONSTRAINT building_type_FK FOREIGN KEY (id_type) REFERENCES type(id_type)\n" +
                "\t,CONSTRAINT building_style_FK FOREIGN KEY (id_style) REFERENCES style(id_style)\n" +
                "\t,CONSTRAINT building_material_FK FOREIGN KEY (id_material) REFERENCES material(id_material)\n" +
                "\t,CONSTRAINT building_roof_type_FK FOREIGN KEY (id_roof_type) REFERENCES roof_type(id_roof_type)\n" +
                "\t,CONSTRAINT building_frame_FK FOREIGN KEY (id_frame) REFERENCES frame(id_frame)\n" +
                ");";


        String createTable_favlist = "CREATE TABLE favlist(\n" +
                "\tid_building    INTEGER NOT NULL ,\n" +
                "\tid_user        INTEGER NOT NULL,\n" +
                "\tCONSTRAINT favlist_PK PRIMARY KEY (id_building,id_user)\n" +
                "\n" +
                "\t,CONSTRAINT favlist_building_FK FOREIGN KEY (id_building) REFERENCES building(id_building)\n" +
                "\t,CONSTRAINT favlist_user0_FK FOREIGN KEY (id_user) REFERENCES user(id_user)\n" +
                ");";

        String[] countries = {"Afrique du Sud","Afghanistan","Albanie","Algérie","Allemagne","Andorre","Angola","Antigua-et-Barbuda","Arabie Saoudite","Argentine","Arménie","Australie","Autriche","Azerbaïdjan","Bahamas","Bahreïn","Bangladesh","Barbade","Belgique","Belize","Bénin","Bhoutan","Biélorussie","Birmanie","Bolivie","Bosnie-Herzégovine","Botswana","Brésil","Brunei","Bulgarie","Burkina Faso","Burundi","Cambodge","Cameroun","Canada","Cap-Vert","Chili","Chine","Chypre","Colombie","Comores","Corée du Nord","Corée du Sud","Costa Rica","Côte d'Ivoire","Croatie","Cuba","Danemark","Djibouti","Dominique","Égypte","Émirats arabes unis","Équateur","Érythrée","Espagne","Eswatini","Estonie","États-Unis","Éthiopie","Fidji","Finlande","France","Gabon","Gambie","Géorgie","Ghana","Grèce","Grenade","Guatemala","Guinée","Guinée équatoriale","Guinée-Bissau","Guyana","Haïti","Honduras","Hongrie","Îles Cook","Îles Marshall","Inde","Indonésie","Irak","Iran","Irlande","Islande","Israël","Italie","Jamaïque","Japon","Jordanie","Kazakhstan","Kenya","Kirghizistan","Kiribati","Koweït","Laos","Lesotho","Lettonie","Liban","Liberia","Libye","Liechtenstein","Lituanie","Luxembourg","Macédoine","Madagascar","Malaisie","Malawi","Maldives","Mali","Malte","Maroc","Maurice","Mauritanie","Mexique","Micronésie","Moldavie","Monaco","Mongolie","Monténégro","Mozambique","Namibie","Nauru","Népal","Nicaragua","Niger","Nigeria","Niue","Norvège","Nouvelle-Zélande","Oman","Ouganda","Ouzbékistan","Pakistan","Palaos","Palestine","Panama","Papouasie-Nouvelle-Guinée","Paraguay","Pays-Bas","Pérou","Philippines","Pologne","Portugal","Qatar","République centrafricaine","République démocratique du Congo","République Dominicaine","République du Congo","République tchèque","Roumanie","Royaume-Uni","Russie","Rwanda","Saint-Kitts-et-Nevis","Saint-Vincent-et-les-Grenadines","Sainte-Lucie","Saint-Marin","Salomon","Salvador","Samoa","São Tomé-et-Principe","Sénégal","Serbie","Seychelles","Sierra Leone","Singapour","Slovaquie","Slovénie","Somalie","Soudan","Soudan du Sud","Sri Lanka","Suède","Suisse","Suriname","Syrie","Tadjikistan","Tanzanie","Tchad","Thaïlande","Timor oriental","Togo","Tonga","Trinité-et-Tobago","Tunisie","Turkménistan","Turquie","Tuvalu","Ukraine","Uruguay","Vanuatu","Vatican","Venezuela","Viêt Nam","Yémen","Zambie","Zimbabwe"};

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable_role);
            stmt.execute(createTable_user);
            stmt.execute(createTable_architect);
            stmt.execute(createTable_type);
            stmt.execute(createTable_time);
            stmt.execute(createTable_style);
            stmt.execute(createTable_roof_type);
            stmt.execute(createTable_material);
            stmt.execute(createTable_country);
            stmt.execute(createTable_city);
            stmt.execute(createTable_frame);
            stmt.execute(createTable_building);
            stmt.execute(createTable_favlist);

            //Insert
            for(int i = 0; i<countries.length; i++){
                String a = "b";
                stmt.execute("INSERT INTO country (label) VALUES (\""+countries[i]+"\");");
            }

            System.out.println("Tables inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
