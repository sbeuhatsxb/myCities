package model;

import entities.*;
import resources.Env;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataHandler implements Env {

    private static final String location = "jdbc:sqlite:";
    private static final String filename = "visitMyCities.db";
    private static final String url = location + filename;

    public static void generatedDatabase() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void newDatabase() {
        System.out.println("Creating database...");
        File f = new File(filename);
        if (!f.exists() && !f.isDirectory()) {
            System.out.println("Wait for a while, the databases is being fed...");
            generatedDatabase();
            createTables();
        } else {
            System.out.println("skipping... Database already created");
        }

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

    public static List<Object> getAll(String table) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM "+table+";";

            ResultSet rs = stmt.executeQuery(query);

            return objectTransformer(rs, table);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Object> getOne(int id, String table) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM "+table+" WHERE id_"+table+" ="+id+";";

            ResultSet rs = stmt.executeQuery(query);

            return objectTransformer(rs, table);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Object> getFilteredIntByColumn(String table, int filter) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM "+table+" WHERE id_"+table+" = "+filter+";";

            ResultSet rs = stmt.executeQuery(query);

            return objectTransformer(rs, table);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Object> getFilteredIntByColumn(String table, int filter, String column) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM "+table+" WHERE "+column+" = "+filter+";";

            ResultSet rs = stmt.executeQuery(query);

            return objectTransformer(rs, table);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static List<Object> objectTransformer(ResultSet rs, String table) throws SQLException {
        List<Object> objectList;
        objectList = new ArrayList();
        Integer userId = null;
        ArrayList<Integer> buildingsId = new ArrayList<>();
        while (rs.next()) {
            switch (table) {
                case ARCHITECT -> {
                    objectList.add(makeArchitect(rs.getInt("id_" + table), rs.getString("label")));
                }
                case BUILDING -> {
                    objectList.add(makeBuilding(
                            rs.getInt("id_" + table),
                            rs.getString("description"),
                            rs.getInt("windows"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getInt("id_architect"),
                            rs.getInt("id_city"),
                            rs.getInt("id_type"),
                            rs.getInt("id_style"),
                            rs.getInt("id_material"),
                            rs.getInt("id_roof_type"),
                            rs.getInt("id_frame")
                    ));
                }
                case CITY ->  {
                    objectList.add(makeCity(rs.getInt("id_" + table), rs.getString("label")));
                }
                case COUNTRY ->  {
                    objectList.add(makeCountry(rs.getInt("id_" + table), rs.getString("label")));
                }
                case FAVLIST ->  {
                            userId = rs.getInt("id_user");
                            buildingsId.add(rs.getInt("id_building"));
                }
                case FRAME ->  {
                    objectList.add(makeFrame(rs.getInt("id_" + table), rs.getString("label")));
                }
                case MATERIAL ->  {
                    objectList.add(makeMaterial(rs.getInt("id_" + table), rs.getString("label")));
                }
                case ROLE ->  {
                    objectList.add(makeRole(rs.getInt("id_" + table), rs.getString("label")));
                }
                case ROOF_TYPE ->  {
                    objectList.add(makeRoofType(rs.getInt("id_" + table), rs.getString("label")));
                }
                case STYLE ->  {
                    objectList.add(makeStyle(rs.getInt("id_" + table), rs.getString("label")));
                }
                case TYPE ->  {
                    objectList.add(makeType(rs.getInt("id_" + table), rs.getString("label")));
                }
                case USER ->  {
                    objectList.add(makeUser(
                            rs.getInt("id_" + table),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getInt("id_role")
                    ));
                }

            }
        }
        if(!table.equals(FAVLIST)){
            return objectList;
        } else {
            objectList.add(makeFavlist(userId, buildingsId));
            return objectList;
        }
    }

    private static Architect makeArchitect(int id, String label){
        Architect object = new Architect();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Building makeBuilding(
            int id,
            String description,
            int windows,
            int year,
            String image,
            int id_architect,
            int id_city,
            int id_type,
            int id_style,
            int id_material,
            int id_roof_type,
            int id_frame
    )
    {
        Building object = new Building();

        object.setId(id);
        object.setWindows(windows);
        object.setYear(year);
        object.setImage(image);
        object.setDescription(description);
        if(getOne(id_architect, ARCHITECT).size() != 0 && !getOne(id_architect, ARCHITECT).equals(null)){
            Architect architect = (Architect) getOne(id_architect, ARCHITECT).get(0);
            object.setArchitect(architect);
        }
        if(getOne(id_city, CITY).size() != 0 && !getOne(id_city, CITY).equals(null)){
            City city = (City) getOne(id_city, CITY).get(0);
            object.setCity(city);
        }
        if(getOne(id_type, TYPE).size() != 0 && !getOne(id_type, TYPE).equals(null)){
            Type type = (Type) getOne(id_type, TYPE).get(0);
            object.setType(type);
        }
        if(getOne(id_frame, FRAME).size() != 0 && !getOne(id_frame, FRAME).equals(null)){
            Frame frame = (Frame) getOne(id_frame, FRAME).get(0);
            object.setFrame(frame);
        }
        if(getOne(id_material, MATERIAL).size() != 0 && !getOne(id_material, MATERIAL).equals(null)){
            Material material = (Material) getOne(id_material, MATERIAL).get(0);
            object.setMaterial(material);
        }
        if(getOne(id_roof_type, ROOF_TYPE).size() != 0 && !getOne(id_roof_type, ROOF_TYPE).equals(null)){
            RoofType roofType = (RoofType) getOne(id_roof_type, ROOF_TYPE).get(0);
            object.setRoofType(roofType);
        }
        if(getOne(id_style, STYLE).size() != 0 && !getOne(id_style, STYLE).equals(null)){
            Style style = (Style) getOne(id_style, STYLE).get(0);
            object.setStyle(style);
        }

        return object;
    }

    private static City makeCity(int id, String label){
        City object = new City();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Country makeCountry(int id, String label){
        Country object = new Country();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Favlist makeFavlist(int user, List<Integer> buildingsId){
        List<Building> buildings = new ArrayList<>();
        for(int id : buildingsId){
            if(getFilteredIntByColumn(Env.BUILDING, id).size() != 0 && !getFilteredIntByColumn(Env.BUILDING, id).equals(null)){
                Building building = (Building) getFilteredIntByColumn(Env.BUILDING, id).get(0);
                buildings.add(building);
            }
        }

        Favlist favlist = new Favlist();

        favlist.setIdUser(user);
        favlist.setBuildings(buildings);
        return favlist;
    }

    private static Frame makeFrame(int id, String label){
        Frame object = new Frame();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Material makeMaterial(int id, String label){
        Material object = new Material();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Role makeRole(int id, String label){
        Role object = new Role();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static RoofType makeRoofType(int id, String label){
        RoofType object = new RoofType();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Style makeStyle(int id, String label){
        Style object = new Style();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static Type makeType(int id, String label){
        Type object = new Type();
        object.setId(id);
        object.setLabel(label);
        return object;
    }

    private static User makeUser(int id, String login, String password, int id_role){
        User object = new User();
        object.setId(id);
        object.setLogin(login);
        object.setLogin(password);
        if(getFilteredIntByColumn(Env.ROLE, id_role).size() != 0 && !getFilteredIntByColumn(Env.ROLE, id_role).equals(null)){
            Role role = (Role) getFilteredIntByColumn(Env.ROLE, id_role).get(0);
            object.setRole(role);
        }

        return object;
    }

    private static void insertNewBuilding(Building building){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String name = ((building.getName() == null) ? null : building.getName());
            String description = ((building.getDescription() == null) ? null : building.getDescription());
            String image = ((building.getImage() == null) ? null : building.getImage());
            int year = ((building.getYear() == 0) ? 0 : building.getYear());
            int windows = ((building.getWindows() == 0) ? 0 : building.getWindows());
            int idCity = ((building.getCity().getId() == 0) ? 0 : building.getCity().getId());
            int idArchitect = ((building.getArchitect().getId() == 0) ? 0 : building.getArchitect().getId());
            int idType = ((building.getType().getId() == 0) ? 0 : building.getType().getId());
            int idStyle = ((building.getStyle().getId() == 0) ? 0 : building.getStyle().getId());
            int idMaterial = ((building.getMaterial().getId() == 0) ? 0 : building.getMaterial().getId());
            int idRoofType = ((building.getRoofType().getId() == 0) ? 0 : building.getRoofType().getId());
            int idFrame = ((building.getFrame().getId() == 0) ? 0 : building.getFrame().getId());

            String query = "INSERT INTO user (name, description, image, year, windows, id_city, id_architect, id_type, id_style, id_material, id_roof_type, id_frame)" +
                    " VALUES (\"" + name + "\", \"" + description + "\", \"" + image + "\", " + year + ", " + windows + ", " + idCity + ", " + idArchitect + ", " + idType + ", " + idStyle + ", " + idMaterial + ", " + idRoofType + ", " + idFrame + ");";
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertNewFavlist(Building building, User user){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            int buildingId = ((building.getId() == 0) ? 0 : building.getId());
            int userId = ((user.getId() == 0) ? 0 : user.getId());

            String query = "INSERT INTO favlist (building_id, user_id)" +
                    " VALUES (" + buildingId + ", " + userId + ");";
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private static void createTables() {

        String createTable_role = "CREATE TABLE role(\n" +
                "\tid_role    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tlabel      TEXT\n" +
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
                "\tname     TEXT,\n" +
                "\tdescription     TEXT,\n" +
                "\timage     TEXT,\n" +
                "\tyear            INTEGER ,\n" +
                "\twindows         INTEGER ,\n" +
                "\tid_city         INTEGER ,\n" +
                "\tid_architect    INTEGER ,\n" +
                "\tid_type         INTEGER ,\n" +
                "\tid_style        INTEGER ,\n" +
                "\tid_material     INTEGER ,\n" +
                "\tid_roof_type    INTEGER ,\n" +
                "\tid_frame        INTEGER\n" +
                "\n" +
                "\t,CONSTRAINT building_city_FK FOREIGN KEY (id_city) REFERENCES city(id_city)\n" +
                "\t,CONSTRAINT building_architect_FK FOREIGN KEY (id_architect) REFERENCES architect(id_architect)\n" +
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

        String[] countriesCapitals = {"Afrique du Sud", "Pretoria", "Afghanistan", "Kaboul", "Albanie", "Tirana", "Algérie", "Alger", "Allemagne", "Berlin", "Andorre", "Andorre-la-Vieille", "Angola", "Luanda", "Antigua-et-Barbuda", "Saint John&rsquo;s", "Arabie Saoudite", "Riyad", "Argentine", "Buenos Aires", "Arménie", "Erevan", "Australie", "Canberra", "Autriche", "Vienne", "Azerbaïdjan", "Bakou", "Bahamas", "Nassau", "Bahreïn", "Manama", "Bangladesh", "Dacca", "Barbade", "Bridgetown", "Belgique", "Bruxelles", "Belize", "Belmopan", "Bénin", "Porto-Novo", "Bhoutan", "Thimphou", "Biélorussie", "Minsk", "Birmanie", "Naypyidaw", "Bolivie", "Sucre", "Bosnie-Herzégovine", "Sarajevo", "Botswana", "Gaborone", "Brésil", "Brasilia", "Brunei", "Bandar Seri Begawan", "Bulgarie", "Sofia", "Burkina Faso", "Ouagadougou", "Burundi", "Gitega", "Cambodge", "Phnom Penh", "Cameroun", "Yaoundé", "Canada", "Ottawa", "Cap-Vert", "Praia", "Chili", "Santiago", "Chine", "Pékin", "Chypre", "Nicosie", "Colombie", "Bogota", "Comores", "Moroni", "Corée du Nord", "Pyongyang", "Corée du Sud", "Séoul", "Costa Rica", "San José", "Côte d'Ivoire", "Yamoussoukro", "Croatie", "Zagreb", "Cuba", "La Havane", "Danemark", "Copenhague", "Djibouti", "Djibouti", "Dominique", "Roseau", "Égypte", "Le Caire", "Émirats arabes unis", "Abou Dabi", "Équateur", "Quito", "Érythrée", "Asmara", "Espagne", "Madrid", "Eswatini", "Mbabane", "Estonie", "Tallinn", "États-Unis", "Washington", "Éthiopie", "Addis-Abeba", "Fidji", "Suva", "Finlande", "Helsinki", "France", "Paris", "Gabon", "Libreville", "Gambie", "Banjul", "Géorgie", "Tbilissi", "Ghana", "Accra", "Grèce", "Athènes", "Grenade", "Saint-Georges", "Guatemala", "Guatemala", "Guinée", "Conakry", "Guinée équatoriale", "Malabo", "Guinée-Bissau", "Bissau", "Guyana", "Georgetown", "Haïti", "Port-au-Prince", "Honduras", "Tegucigalpa", "Hongrie", "Budapest", "Îles Cook", "Avarua", "Îles Marshall", "Majuro", "Inde", "New Delhi", "Indonésie", "Jakarta", "Irak", "Bagdad", "Iran", "Téhéran", "Irlande", "Dublin", "Islande", "Reykjavik", "Israël", "Jérusalem", "Italie", "Rome", "Jamaïque", "Kingston", "Japon", "Tokyo", "Jordanie", "Amman", "Kazakhstan", "Noursoultan", "Kenya", "Nairobi", "Kirghizistan", "Bichkek", "Kiribati", "Tarawa-Sud", "Koweït", "Koweït", "Laos", "Vientiane", "Lesotho", "Maseru", "Lettonie", "Riga", "Liban", "Beyrouth", "Liberia", "Monrovia", "Libye", "Tripoli", "Liechtenstein", "Vaduz", "Lituanie", "Vilnius", "Luxembourg", "Luxembourg", "Macédoine", "Skopje", "Madagascar", "Antananarivo", "Malaisie", "Kuala Lumpur", "Malawi", "Lilongwe", "Maldives", "Malé", "Mali", "Bamako", "Malte", "La Valette", "Maroc", "Rabat", "Maurice", "Port-Louis", "Mauritanie", "Nouakchott", "Mexique", "Mexico", "Micronésie", "Palikir", "Moldavie", "Chișinău", "Monaco", "Monaco", "Mongolie", "Oulan-Bator", "Monténégro", "Podgorica", "Mozambique", "Maputo", "Namibie", "Windhoek", "Nauru", "Yaren", "Népal", "Katmandou", "Nicaragua", "Managua", "Niger", "Niamey", "Nigeria", "Abuja", "Niue", "Alofi", "Norvège", "Oslo", "Nouvelle-Zélande", "Wellington", "Oman", "Mascate", "Ouganda", "Kampala", "Ouzbékistan", "Tachkent", "Pakistan", "Islamabad", "Palaos", "Ngerulmud", "Palestine", "Ramallah", "Panama", "Panama", "Papouasie-Nouvelle-Guinée", "Port Moresby", "Paraguay", "Asuncion", "Pays-Bas", "Amsterdam", "Pérou", "Lima", "Philippines", "Manille", "Pologne", "Varsovie", "Portugal", "Lisbonne", "Qatar", "Doha", "République centrafricaine", "Bangui", "République démocratique du Congo", "Kinshasa", "République Dominicaine", "Saint-Domingue", "République du Congo", "Brazzaville", "République tchèque", "Prague", "Roumanie", "Bucarest", "Royaume-Uni", "Londres", "Russie", "Moscou", "Rwanda", "Kigali", "Saint-Kitts-et-Nevis", "Basseterre", "Saint-Vincent-et-les-Grenadines", "Kingstown", "Sainte-Lucie", "Castries", "Saint-Marin", "Saint-Marin", "Salomon", "Honiara", "Salvador", "San Salvador", "Samoa", "Apia", "São Tomé-et-Principe", "São Tomé", "Sénégal", "Dakar", "Serbie", "Belgrade", "Seychelles", "Victoria", "Sierra Leone", "Freetown", "Singapour", "Singapour", "Slovaquie", "Bratislava", "Slovénie", "Ljubljana", "Somalie", "Mogadiscio", "Soudan", "Khartoum", "Soudan du Sud", "Djouba", "Sri Lanka", "Sri Jayawardenapura", "Suède", "Stockholm", "Suisse", "Berne", "Suriname", "Paramaribo", "Syrie", "Damas", "Tadjikistan", "Douchanbé", "Tanzanie", "Dodoma", "Tchad", "N&rsquo;Djaména", "Thaïlande", "Bangkok", "Timor oriental", "Dili", "Togo", "Lomé", "Tonga", "Nukuʻalofa", "Trinité-et-Tobago", "Port-d&rsquo;Espagne", "Tunisie", "Tunis", "Turkménistan", "Achgabat", "Turquie", "Ankara", "Tuvalu", "Funafuti", "Ukraine", "Kiev", "Uruguay", "Montevideo", "Vanuatu", "Port-Vila", "Vatican", "Vatican", "Venezuela", "Caracas", "Viêt Nam", "Hanoï", "Yémen", "Sanaa", "Zambie", "Lusaka", "Zimbabwe", "Harare"};
        String[] styles = {"Architecture en béton", "Architectureenbéton", "Architecture en bois", "Architectureenbois", "Architecture ottomane", "Architecture ottomane", "Architectureottomane", "Architectureparpériode", "Architecture traditionnelle", "Architecture traditionnelle", "Architecturetraditionnelle", "Artdéco",
                "Art nouveau", "Architecture Art nouveau", "Architecture baroque", "Bauhaus", "Architecture brutaliste", "Architecture byzantine", "Architecture carolingienne", "Architecture classique", "Architecture coloniale",
                "Architecture constructiviste", "Architecture déconstructiviste", "Architecture éclectique", "Architecture expressionniste",
                "Architecture futuriste", "Architecture gothique", "Gothique isabélin", "Architecture high-tech", "Architecture industrielle", "Architecture maniériste",
                "Style manuélin", "Style architectural médiéval", "Architecture métaboliste", "Architecture métallique",
                "Architecture moderne", "Architecture mudéjare", "Architecture néo-baroque", "Architecture néo-byzantine",
                "Architecture néoclassique", "Architecture néo-gothique", "Architecture néo-mauresque", "Architecture renaissance", "Architecture néo-romane",
                "Architecture néo-russe", "Architecture paléochrétienne", "Postmodernisme (architecture)", "Architecture régionaliste",
                "Architecture romano-byzantine", "Architecture stalinienne", "Style international", "Style architectural tibétain", "Architecture totalitaire",
                "Architecture victorienne"};

        String[] types = {"Structure d'accueil pour personnes âgées et personnes handicapées","Salle d'audition, de conférences, de réunions, de spectacles ou à usages multiples","Magasin de vente, Centres commerciaux","Restaurant et débit de boissons","Hôtel et pension de familles","Salle de danse et salles de jeux","Etablissement d'enseignement","Bibliothèque, centre de documentation","Salle d'expositions","Etablissement de soin","Etablissement de culte","Administration, banques, bureaux","Etablissement sportif couvert","Musée","Etablissement de plein air","Chapiteau, tente et structure","Structure gonflable","Parc de stationnement couvert","Gare accessible au public","Hôtel-restaurant d'altitude","Etablissement flottant","Refuge de montagne", "Palais", "Obélisque", "Capitole", "Cathédrale", "Chapelle", "Temple", "Forteresse", "Tour"};

        String[] roles = {"admin", "user"};
        String[] frames = {"bois", "métal"};
        String[] roofTypes = {
                "Toiture à 2 pans ou toiture à 2 versants",
                "Toiture à 4 pans ou toiture à 4 versants",
                "Toiture à demi-croupe",
                "Toiture à demi-croupe et brisis",
                "Toit terrasse ou toit plat",
                "Toit monopente",
                "Toiture papillon",
                "Toiture à la Mansart",
                "Comble aménagé avec lucarnes",
                "Toiture en dents de scie"
        };
        String[] materials = {"pierre", "plâtre", "ciment", "chaux", "brique", "bloc", "pavé", "tuile", "ardoise", "carreau", "bardeau", "panneau de menuiserie", "produit étirés", "verre étiré", "feuille d'acier", "feuille de plomb", "feuille de zinc", "membranes synthétique", "produit laminés", "poutrelles", "barres", "fils en acier ou fer forgé"};

        String[] architects = {
                "Odorannus de Sens","Hugues, dit VGo","J","Jean le Bouteiller","S","Guillaume de Sens (architecte)","Bertrand de la Bacalaria","Étienne de Bonneuil","C","Jehan de Chelles","Renaud de Cormont","Thomas de Cormont","Robert de Coucy","D","Jean Deschamps (architecte)","E","Eudes de Montreuil","J","Jean-le-Loup","L","Hugues Libergier","Robert de Luzarches","M","Pierre de Montreuil","O","Jean d'Orbais","P","Pierre d'Angicourt","R","Gaucher de Reims","S","Jacques de Saint-Georges","Bernard de Soissons","V","Gautier de Varinfroy","Villard de Honnecourt","Pierre de Cébazat","Pierre de Chelles","Robert de Coucy","D","Drouet de Dammartin","Guy de Dammartin","F","Jacques de Fauran","J","Jacques de Beaujeu","L","Jean de Liège (architecte)","Sicard de Lordat","M","Mathieu d'Arras","N","Nicolas de Bonaventure","P","Pierre Perrat","R","Jean Ravy","S","Jacques de Saint-Georges","T","Raymond du Temple","Colin Biart","Jean Bornoy","C","Martin Chambiges","Nicolas Chantereine","D","Jean de Dammartin","G","Jean Gendrot","J","Jacques de Beaujeu","Jean de Commercy","L","Blaise Lécuyer","Roulland Le Roux","P","Guillaume Pontifs","R","Mathieu Reguaneau","Guillaume Robin","Mathurin Rodier","S","Jenson Salvart","T","Pierre Tarisel","V","Vauzy de Saint-Martin","Famille Androuet du Cerceau","Baptiste Androuet du Cerceau","Jacques Ier Androuet du Cerceau","Jacques II Androuet du Cerceau","B","Dominique Bachelier","Nicolas Bachelier","Jehan de Beauce","Colin Biart","Katherine Briçonnet","Salomon de Brosse","Jean Bullant","C","Jean Martinet (architecte)","Jean Chambige","Martin Chambiges","Pierre Chambiges","Nicolas Chantereine","D","Jean Delespine (architecte)","Philibert Delorme","Étienne Dupérac","F","Gabriel Favereau","Jehan de Félin","G","Jean Goujon","H","Simon Hayneufve","J","Jacquette de Montbron","Jean de Beaujeu (architecte)","L","Gilles Le Breton","Nicolas Le Mercier","Roulland Le Roux","Pierre Lescot","M","Étienne Martellange","Clément Métezeau","Thibault Métezeau","N","Bernard Nalot","Pierre Nepveu (architecte)","P","Guillaume Pelvoysin","R","Mathieu Reguaneau","Nicolas Ribonnier","S","Antoine Salvanh","Jean Salvanh","Hugues Sambin","Hector Sohier","Pierre Souffron","Jacques Sourdeau","V","Famille de la Vallée","Jacques Aleaume","Famille Androuet du Cerceau","Jean Androuet du Cerceau","Augustin-Charles d'Aviler","B","Guillaume Bauduer","François Blondel","Jean-Baptiste Bouchardon","Jacques Bougier","Jean-Baptiste Broebes","Jacques Bruant","Libéral Bruand","Jean-Baptiste Bullet de Chamblain","C","Bernard Campmartin","Tugal Caris","Isaac de Caus","Salomon de Caus","Charles Chamois","Étienne Corbineau","Gilles Corbineau","Jacques Corbineau","Pierre Corbineau","Pierre Cottard","Frémin de Cotte","Robert de Cotte","Jacques Cubizol","D","Gabriel Dardaillon","Jean Delamonce","Nicolas II Delespine","Pierre Delisle-Mansart","Olivier Delourme","François Derand","Girard Desargues","Antoine Desgodets","Julien Destrée","Jean Dubois (sculpteur)","Guillaume Ducellet","Pierre Duplessy-Michel","E","Charles Errard","F","André Félibien","Jean-François Félibien","G","Jacques Gabriel (mort en 1628)","Jacques Gabriel (1630-1686)","Jacques Gabriel (1605-1662)","Christophe Gamard","Germain Gaultier","Jacques Gentillâtre","Giral (architectes)","Daniel Gittard","Thomas Gobert (architecte)","Pierre Gole","H","Jules Hardouin-Mansart","Abraham Hardouin","Pierre Hardouin","J","Gilles Jodelet de La Boissière","L","Pierre Lambert (architecte)","François Langlois (architecte)","Langlois (architectes)","Simon de la Vallée","Nicolas Le Mercier","Pierre Le Muet","Philibert Le Roy","François Le Vau","Louis Le Vau","Gabriel Le Duc","Pierre Léglise (maître-maçon)","Jean Lejuge","Jacques Lemercier","Michel Lemesle","Antoine Lepautre","Pierre Lepautre (1652-1716)","Pierre Levesville","M","François Mansart","Daniel Marot","Jean Marot (architecte)","Étienne Martellange","Olivier Martinet","Jean-Baptiste Mathey","Simon Maupin","Clément II Métezeau","Louis Métezeau","Pierre II Mignard","Pierre Miressou","N","Louis Noblet","O","François Ier d'Orbay","François II d'Orbay","P","Claude Pacot","Blaise François Pagan","Jacques Peitret","Claude Perrault","Jean Péru","Pierre Pommeyrol","Thomas Poussin","Pierre Puget","R","Jean-Claude Rambot","Nicolas Rambourg","Eustache Restout","Jean-Pierre Rivalz","Jonas Robelin","Marc Robelin","François Romain","S","Sébastien de Saint-Aignan","Didier Sansonnet","Pierre Souffron","Gabriel Soulignac","T","Claude Terrin","V","François de Royers de La Valfenière","Famille de la Vallée","Marin de la Vallée","Laurent Vallon","Sébastien Le Prestre de Vauban","Claude Vellefaux","Simon Vollant","Joseph Abeille","Antoine d'Allemand","Anatole Amoudru","Denis Antoine","Antoine-Charles Aubert","Jean Aubert (architecte)","Claude-Louis d'Aviler","B","François Antoine Babuty-Desgodets","Claude Bacarit","Thomas Pierre Baraguey","Michel Bardoul de la Bigottière","Jean-Benoît-Vincent Barré","François Dominique Barreau de Chefdeville","Jean Beausire","Jean-Baptiste Augustin Beausire","Jérôme Beausire","François-Joseph Bélanger","Claude Billard de Bélisard","Jérôme Charles Bellicard","Jean-Charles Bellu","Charles Joachim Bénard","Joseph Bénard","Pierre-Nicolas Bénard","Louis Benoist (architecte)","Louis-Martin Berthault","Jean-Baptiste Berthier","Claude Joseph Alexandre Bertrand","Charles-Jacques Billaudel","Jean-René Billaudel","Jacques-François Blondel","Jean-François Blondel","Joachim Bocher","Germain Boffrand","Pierre Bondon","Richard-François Bonfin","Jacques-Charles Bonnard","Pierre Boscry","Jean-Baptiste Bouchardon","Juste-Nathan François Boucher","Jean-Louis Bouet","Étienne-Louis Boullée","Antoine Joseph de Bourge","Maximilien Brébion","Louis Bretez","Charles-Étienne Briseux","Jean-Baptiste Broebes","Alexandre-Théodore Brongniart","Joseph Brousseau","Ignace François Broutin","Esprit-Joseph Brun","Jean-Ange Brun","Pierre-Gabriel Bugnet","Jean-Baptiste Bullet de Chamblain","C","Jean Cailleteau","Pierre Cailleteau","François Cammas","Guillaume Cammas","Pierre Caqué","Jean-Michel Carbonnar","Jean-Baptiste Caristie","François Carlier (architecte)","René Carlier (architecte)","Jean-Sylvain Cartaud","Jean-Baptiste Ceineray","Jacques Cellerier","Jean-François Chalgrin","Charles-Michel-Ange Challe","Jean-Baptiste Chaussard","Mathurin Cherpitel","Auguste Cheval de Saint-Hubert","Jean-Michel Chevotet","Antoine Choquet de Lindu","Charles Christiani","Claude Antoine Colombot","Charles-Louis Clérisseau","Claude Cochet","François Cointeraux","Louis-Marie Colignon","Jean-Baptiste Collet","Jean-Charles Colombot","Jean-François Colson","Louis Combes (architecte)","Pierre Contant d'Ivry","Théodore Cornut","Jules-Robert de Cotte","Louis de Cotte","Robert de Cotte","Jean Courtonne","Jean-Baptiste Courtonne","Charles-Pierre Coustou","Guillaume-Martin Couture","Joseph-Abel Couture","Mathurin Crucy","D","Victor-Thierry Dailly","Michel Dal Gabbio","Louis-Emmanuel-Aimé Damesme","Jean Damun","Charles François Darnaudin","François-Antoine Davy de Chavigné","François Debias-Aubry","Jean-Pierre Defrance","Jean Charles Delafosse","Claude-Mathieu Delagardette","Pierre-Alexis Delamair","Ferdinand-Sigismond Delamonce","Jean Delamonce","François-Jacques Delannoy","Nicolas II Delespine","Pierre-Jules Delespine","Antoine Derizet","Antoine Desgodets","Claude Desgots","Pierre Desmaisons","Antoine-Victor Desmarais","Louis-Jean Desprez","Louis Devilliars","Claude François Devosge","Charles De Wailly","Benjamin Joseph Dewarlez","Jacques Donnat","Julien-François Douillard","Baltasar Dreveton","Nicolas Ducret","Jean-Baptiste Dufart","Alexandre Dufour (architecte)","Léon Dufourny","Nicolas Dulin","Gabriel Pierre Martin Dumont","Thomas Dumorey","Jean-Nicolas-Louis Durand","Léopold Durand","F","Jean-François Félibien","Nicolas-Martial Foacier","François Forestier de Villeneuve","Jacques Foucherot","François Franque","Jean-Baptiste Franque","G","Ange-Antoine Gabriel","Ange-Jacques Gabriel","Jacques Gabriel (1667-1742)","Jean-Pierre Galezot","Bertrand Garipuy","Jean-Charles Garnier d'Isle","Émiland Gauthey","Thomas Germain","Giral (architectes)","Nicolas-Claude Girardin","Alexandre Jean-Baptiste Guy de Gisors","Jacques-Pierre Gisors","Pierre Gittard","Pierre François Godot","Georges-Claude Goiffon","François-Joseph Gombert","Thomas-Joseph Gombert","Jacques Gondouin","Claude-Martin Goupy","Martin I Goupy","Martin II Goupy","Louis-Robert Goust","Antoine Groignard","Jacob Guerne","François Guéroult","Charles-Axel Guillaumot","Claude Guillot-Aubry","Barnabé Guimard","H","Jacques Haneuse","Jacques Hardouin-Mansart de Sagonne","Jules Michel Alexandre Hardouin","Jean-Baptiste Philippe Harou","Michel-Barthélemy Hazon","Pierre-Louis Helin","Jean-François Heurtier","Houdault (architectes)","François-Simon Houlié","François Huguet","Jean Hupeau","Maximilien Joseph Hurtault","Jean-Jacques Huvé","I","Pierre-Michel d'Ixnard","J","Jean-Bernard-Abraham Jacquemin","Pierre Jacquot de Villeneuve","Jean-Nicolas Jadot de Ville-Issey","Claude Jean-Baptiste Jallier de Savault","Nicolas-Henri Jardin","Pierre-Sylvestre Jaunez","Barthélemy Jeanson","Jean-Nicolas Jennesson","Denis Jossenay","K","Jean-Joseph Kapeller","L","Jean-Baptiste de La Rue","Éloi Labarre","Hyacinthe de Labat de Savignac","Étienne Laclotte","Barthélémy Lafon","Jacques de La Guépière","Philippe de la Guépière","Thomas Lainée","François Laurent Lamandé","Pierre Lambert (architecte)","Paul Abadie","Paul Abadie père","Jean-Marie Abgrall (historien)","Charles Abric","Gustave Alaux (architecte)","Gustave Alavoine","Jean-Antoine Alavoine","Camille Albert","Alfred-Philibert Aldrophe","Gaudensi Allar","Achille Ambialet","Gabriel-Auguste Ancelet","Charles André","Émile André","Gaspard André","Louis-Jules André","Pierre André (architecte)","Georges Antoine (homme politique)","Louis Henry Antoine","Albert Touzin","Germain Sauvanet","Alfred Armand","Charles Arnaud (architecte)","Édouard Arnaud","Ferdinand Arnodin","Jean Jacques Nicolas Arveuf-Fransquin","Antoine-Charles Aubert","Pierre Aublé","Alexandre Émile Auburtin","Auguste-Henri Vildieu","Paul Auscher","Émile Auvray","B","Henry Bach","Léon Bachelin","Antoine-Nicolas Bailly","Ferdinand Bal","Achille Ballière","Albert Ballu","Théodore Ballu","Louis-Pierre Baltard","Victor Baltard","Charles-Louis Balzac","Thomas Pierre Baraguey","André de Baralle","Henri de Baralle","Julien Barbier","Louis Barbotin (architecte)","Charles Baron (architecte)","Ferdinand Joseph Hippolyte Barqui","Jacques-Eugène Barthélémy","Edmond Auguste Bastien","Jules Batigny","Carlos Batteur","Anatole de Baudot","Ambroise Baudry","Jacques Baumier","Amand Louis Bauqué","François-Joseph Bélanger","Louis Belin","Philibert Bellemain","Émile Bénard","Joseph Bénard","Pierre-Nicolas Bénard","Bénigne Claude Alfred Chevrot","Claude-Anthelme Benoit","Léon Bénouville","Pierre Louis Bénouville","Lucien Bentz","Charles Benvignat","Édouard Bérard","Jean-Baptiste Bernard","Charles-Louis Bernier","Louis Bernier","Alfred Berruyer","Louis-Martin Berthault","André Berthier (architecte)","Arthur Bertin","Paul Bertrand (architecte)","Adolphe Berty","Hippolyte Béziers-Lafosse","Sébastien-Marcel Biasini","Anatole Bienaimé","Georges Biet","Joseph Bigot","Charles Billoré","René Binet (architecte)","Jean-Prosper Bissuel","Prosper-Édouard Bissuel","Victor-Auguste Blavette","Étienne Blon","Henri Blondel","Paul Blondel","Guillaume Abel Blouet","Auguste Bluysen","Prosper Bobin","François-Adolphe Bocage","Émile Boeswillwald","Paul Boeswillwald","Louis-Auguste Boileau","Louis-Charles Boileau","Étienne-Joseph Boissonnade","Louis Boitte","Louis-Michel Boltz","Philippe-Alexandre-Louis Bommart","Richard-François Bonfin","Jacques-Charles Bonnard","Louis Bonnier","Ernest Bosc","Pierre Bossan","Louis-Jules Bouchot","Pierre-Valentin Boudhors","Jean-Louis Bouet","François Bougoüin","Auguste Louis Édouard Bouillon","Antoine-François Bourbon","Jules Bourdais","Gaston Bourdon","Théophile Bourgeois","Gustave Bourgerel","Jules Bourgoin","Charles-Désiré Bourgon","Gustave Bourrières","Jean Boussard","Maurice Boutterin","Joseph-Antoine Bouvard","Richard Bouwens van der Boijen","William Bouwens van der Boijen","Jean Bréasson","Gabriel Bréfeil","Louis-Antoine-Maurice Bresson","Georges Le Breton","Alexandre-Théodore Brongniart","Antoine Brossard","Emmanuel Brune","Marie-Joseph Brune","Narcisse Brunette","Ernest Brunnarius","Louis-Clémentin Bruyerre","Pierre-Gabriel Bugnet","Fernand Buisset","C","Jules Calbairac","Félix Callet","Pierre Caloine","Camille Bodin-Legendre","Henri Cannissié","Philippe Cannissié","Auguste Caristie","Charles Émile Carré","Joseph Cassien-Bernard","Eugène-Toussaint Cateland","Louis Catoire","Léon Cayotte","Jacques Cellerier","François-Alexis Cendrier","Pierre Chabat","Pierre Prosper Chabrol","Wilbrod Chabrol","Henri Chaine","Jean-François Chalgrin","Alban Chambon","Alfred Chapon","Paul Charbonnier","Théodore Charpentier","Léon Charvet","Charles Frédéric Chassériau","Charles-François Chatelain","André Chatillon","Benoît-Joseph Chatron","Jules Chatron","François-Benjamin Chaussemiche","Joseph-Fleury Chenantais","Mathurin Cherpitel","François-Auguste Cheussey","Jean-Baptiste Chevalt","Antoine-Marie Chenavard","Charles Chipiez","Antonin Chomel","Augustin Chomel","François Clasquin","Claude Antoine Colombot","Gustave Clausse","Charles-Louis Clérisseau","Pierre Clochar","Georges Closson","Joseph Clugnet","Claude Cochet","François Cointeraux","Jean Geoffroy Conrath","Simon-Claude Constant-Dufeux","Henri Contamine","Georges-Ernest Coquart","Adolphe Coquet","Jean Baptiste Cordonnier","Louis Marie Cordonnier","Édouard Corroyer","Pascal Coste","Léonce Couëtoux","Octave Courtois-Suffit","Élie Courtonne","Claude-Philippe Cramail","Charles Abella","Charles Adda","Henri Adé","Georges Adilon","Jacques Adnet","Jean-Paul Alaux","Dominique Alba","Georges Albenque","Camille Albert","Alexandre Fournier","Philippe Ameller","Michel Andrault","Charles André","Émile André","Gaspard André","Jacques et Michel André","Pierre André (architecte)","Paul Andreu","Pierre Ansart (architecte)","André Arbus","Architectes des palais de Nice","André Arfvidson","Henri Armbruster (architecte)","Léon Arnal","Charles Arnaud (architecte)","Édouard Arnaud","Charles Arnault (1890-1950)","Ferdinand Arnodin","Arthur Chaudouet","Jules-Godefroy Astruc","Pierre Aublé","Louis Aublet","François Aubry (architecte)","Jacques Marcel Auburtin","Victor Auclair","Alfred Audoul","Roger Aujame","Paul Auscher","Édouard Autant","Émile Auvray","B","Léon Bachelin","Éric Bagge","Georges-Eugène Balleyguier","Albert Ballu","Raymond Barbaud","Pierre Barbe","Julien Barbier","Henri Bard","Renaud Bardon","Carlos Batteur","Anatole de Baudot","Édouard Bauhain","Yves Bayard","Gérard Beau de Loménie","Henri Beauclair (architecte)","Jean Beaugrand","Emmanuel Bellini","Dom Bellot","Émile Bénard","Lucien Bentz","André Bérard (architecte)","Édouard Bérard","Patrick Berger (architecte)","René-Félix Berger","Henry Bernard","Louis Bernier","Jack Berthelot","Marc Berthier","Arthur Bertin","Émile Bertone","Paul Bertrand (architecte)","Léon Besnard","Charles-Henri Besnard","Léon Bénard","Arsène Bical","Anatole Bienaimé","Georges Biet","Paul Bigot","Philippe Bigot (architecte)","Juliette Billard","René Binet (architecte)","Prosper-Édouard Bissuel","Maurice Blanc (architecte)","Jean-François Blassel","Victor-Auguste Blavette","André Bloc","Henri-Léon Bloch","Auguste Bluysen","Prosper Bobin","François-Adolphe Bocage","Jean-François Bodin (architecte)","Paul Boeswillwald","Xavier Bohl","Camille Boignard","Louis-Hippolyte Boileau","Pierre Boille","Yves Boiret","Guillaume du Boisbaudry","Jean Boissel","André Boll","Patrice Bonnet","Louis Bonnier","Philippe Bonnin (architecte)","David Bonpain","Dariush Borbor","Pierre Bourdeix","Frédéric Borel","Auguste Bossu","Jean Bossu (architecte)","Patrick Bouchain","Jean Boucher (architecte)","André Boucton","Marcel Boudin","Mohamed Boudjedra","Philippe Boudon","Jean Boudriot","Bernard Bougeault","Florence Bougnoux","Joseph Bougoüin","Paul Bougoüin","James Bouillé","Olivier Bouillère","Fernand Boukobza","Antoine-François Bourbon","Alain Bourbonnais","Charles Bourgeois (architecte)","Théophile Bourgeois","Charles-Désiré Bourgon","Pierre Bourineau","Émile Boutin (peintre)","Maurice Boutterin","Joseph-Antoine Bouvard","Roger Bouvard","Richard Bouwens van der Boijen","Marius Boyer","Louis Brachet","Raoul Brandon","Louis Brassart-Mariage","Reinhart Braun","Albert-Louis Bray","Jean Bréasson","Georges Le Breton","Marc Brillaud de Laujardière","Edmond Brion","Yves Brunier (paysagiste)","Raoul Brygoo","Iwona Buczkowska","Jean-Pierre Buffi","Fernand Buisset","Joseph Bukiet","Raymond Busse","Rémy Butler","C","Christian Cacaut","Olivier-Clément Cacoub","Auguste Cadet","Robert Cado","Jules Calbairac","Henri-Jean Calsat","Albert Caquot","William Cargill","Pierre-Louis Carlier","Jacques Carlu","François Carpentier","Joseph Carré","Louis de Casabianca","Bernard Casnin","Urbain Cassan","Joseph Cassien-Bernard","Gaston Castel","Roland Castro (architecte)","Amédée Cateland","Emmanuel Cateland","Jean Cateland","Louis Cauvin (architecte)","César Cavallin","Léon Cayotte","Patrick Céleste","Wilbrod Chabrol","Gérard Chamayou","Alban Chambon","Jean-Louis Chanéac","Marcel Chaney","Marcel Chappey","Laurent Chappis","Paul Charbonnier","Pierre Chareau","Charles Javelle","Claude Charpentier","Jean-Marie Charpentier","Léon Charvet","André Chatelin","François Chatillon (architecte)","Utilisateur:Xcvbn,gfds/Brouillon","Eugène Chauliat","Olivia Chaumont","François-Benjamin Chaussemiche","Charles Chaussepied","André Chauvet","Georges Chedanne","André Cheftel","Delphine Aboulker","Dominique Alba","Philippe Ameller","Paul Andreu","Silvio d'Ascia","Roger Aujame","Jean François Authier","B","Yves Ballot","Marc Barani","Yves Bayard","Alun Be","Patrick Berger (architecte)","Marc Berthier","Christian Biecher","Grégoire Bignier","Clément Blanchet","Jean-François Blassel","Jean-François Bodin (architecte)","Xavier Bohl","Yves Boiret","Philippe Bonnin (architecte)","Frédéric Borel","Patrick Bouchain","Philippe Boudon","Florence Bougnoux","Olivier Bouillère","Iwona Buczkowska","Jean-Pierre Buffi","Rémy Butler","C","Pierre-Louis Carlier","Roland Castro (architecte)","Patrick Céleste","François Chatillon (architecte)","Utilisateur:Xcvbn,gfds/Brouillon","Olivia Chaumont","Alexandre Chemetoff","Paul Chemetov","Christophe Cheron","Jean Chollet (scénographe)","Olivier Cinqualbre","Henri Ciriani","Henri Colombani","Vincen Cornu","Patrick Coulombel","Didier Courbot","Pascal Cribier","D","Sophie Dabat","Christine Dalnoky","Odile Decq","Luc Delemazure","Anne Démians","Philippe Demougeot","François Depresle","Jean-Paul Desbat","Véronique Descharrières","François Deslaugiers","Bernard Desmoulin","Thierry Despont","Christian Devillers","Borhene Dhaouadi","Sirandou Diawara","Frédéric Didier","Joseph Dirand","Christian Drevet","Frédéric Druot","Sylvain Dubuisson","Laurent Duport","Jean-Marie Duthilleul","E","Gilles Ebersolt","Luce Eekman","F","Adrien Fainsilber","Pierre-Louis Faloci","Didier Faustino","Pierre Ferret (1949)","Jacques Ferrier","Stanislas Fiszer","Bruno Fortier","Philippe Fraleu","Édouard François","Franklin Azzi","Frédéric Bonnet","Françoise Fromonot","Catherine Furet","G","Jean de Gastines","Pierre-Antoine Gatier","Bruno Gaudin","Henri Gaudin","Manuelle Gautrand","Philippe Gazeau","Lina Ghotmeh","Victor Gingembre","Édith Girard","Charles Goldblum","Gérard Grandval","François Grether","Antoine Grumbach","Pascale Guédot","Claire Guieysse","H","Franck Hammoutène","Christian Hauvette","Jean-Marie Hennin","Bruno Huerre","I","Jean-Marc Ibos et Myrto Vitart","J","Catherine Jacquot","Jean-François Jodry","Véronique Joffre (architecte)","Françoise-Hélène Jourda","K","Daniel Kahane","Kardham Cardete Huet Architecture","Frédéric Keiff","Mahmoud Keldi","L","Christophe Lab","Julien Labrousse","Anne Lacaton","Denis Laming","Didier Laroque","Gaëlle Lauriot-Prévost","Thomas Lavigne","Jacques-Émile Lecaron","François Leclercq","Bertrand Lemoine","Sylvain Lhermitte","Christian Liaigre","Yves Lion","Jacques Lucan","Dominique Lyon","M","Philippe Madec","India Mahdavi","Arthur Mamou-Mani","David Mangin","Didier Maufras","Fiona Meadows","Thierry Melot","Brigitte Métra","Nicolas Michelin","Marc Mimram","Marine Miroux","Alain Moatti","Pascal Morabito","Jean-Paul Morel","Michel Mossessian","Jacques Moulin (architecte)","N","Nicolas Normier","Jean Nouvel","O","Olivier Saguez","P","Philippe Panerai","Ephraim Henry Pavie","Jean-Michel Payet","Utilisateur:De gorostarzu Patricia/Fabrice Peltier","Georges Pencreac'h","Gaëlle Péneau","Marc Perelman","Gilles Perraudin","Dominique Perrault","Alain-Charles Perrot","William Pesson","Jean-Paul Philippon","Antoine Picon","Jean Pistre","Jean-Loup Pivin","Christian de Portzamparc","Elizabeth de Portzamparc","Philippe Prost","Allain Provost","R","Reichen et Robert","Michel Rémon","Rudy Ricciotti","Olivier Rigaud","Jacques Ripault","Adeline Rispal","Antoinette Robain","Marc Rolinet","Pascal Rollet","Jean-Loup Roubert","Jacques Rougerie (architecte)","Frédéric Ruyant","S","Serge Salat","Alain Sarfati (architecte)","Maurice Sauzet","François Scali","Christiane Schmuckle-Mollard","Richard Scoffier","François Seigneur","David Serero (architecte)","Lotfi Sidirahal","Jacques Simon (paysagiste)","Francis Soler","Nicolas Soulier","Isabelle Stanislas","Philippe Starck","Antoine Stinco","T","Jad Tabet","Roger Taillibert","Annette Tison","Hervé Tordjman","Daniel Treiber","V","Marc Van Peteghem","Jean-Philippe Vassal","Jean-Yves Veillard","Michel Vernes"
        };

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTable_role);
            stmt.execute(createTable_user);
            stmt.execute(createTable_architect);
            stmt.execute(createTable_type);
            stmt.execute(createTable_style);
            stmt.execute(createTable_roof_type);
            stmt.execute(createTable_material);
            stmt.execute(createTable_country);
            stmt.execute(createTable_city);
            stmt.execute(createTable_frame);
            stmt.execute(createTable_building);
            stmt.execute(createTable_favlist);

            //Insert Countries cities
            for (int i = 0; i < countriesCapitals.length; i += 2) {
                String countryInsert = "INSERT INTO country (label) VALUES (\"" + countriesCapitals[i] + "\");";
                String capitalsInsert = "INSERT INTO city (label, id_country) VALUES (\"" + countriesCapitals[i + 1] + "\", (SELECT id_country FROM country WHERE label = \"" + countriesCapitals[i] + "\"));";
                stmt.execute(countryInsert);
                stmt.execute(capitalsInsert);
            }

            //Insert styles
            for (int i = 0; i < styles.length; i++) {
                String query = "INSERT INTO style (label) VALUES (\"" + styles[i] + "\");";
                stmt.execute(query);
            }

            //Insert roles
            for (int i = 0; i < roles.length; i++) {
                String query = "INSERT INTO role (label) VALUES (\"" + roles[i] + "\");";
                stmt.execute(query);
            }

            //Insert frames
            for (int i = 0; i < frames.length; i++) {
                String query = "INSERT INTO frame (label) VALUES (\"" + frames[i] + "\");";
                stmt.execute(query);
            }

            //Insert types
            for (int i = 0; i < types.length; i++) {
                String query = "INSERT INTO type (label) VALUES (\"" + types[i] + "\");";
                stmt.execute(query);
            }

            //Insert roofTypes
            for (int i = 0; i < roofTypes.length; i++) {
                String query = "INSERT INTO roof_type (label) VALUES (\"" + roofTypes[i] + "\");";
                stmt.execute(query);
            }

            //Insert materials
            for (int i = 0; i < materials.length; i++) {
                String query = "INSERT INTO material (label) VALUES (\"" + materials[i] + "\");";
                stmt.execute(query);
            }

            //Insert architects
            for (int i = 0; i < architects.length; i++) {
                if(architects[i].length() > 1){
                    String query = "INSERT INTO architect (label) VALUES (\"" + architects[i] + "\");";
                    stmt.execute(query);
                } else {
                    System.out.println("Skipping architect named "+architects[i]);
                }
            }

            //Insert favorites
            for (int i = 1; i <= 4 ; i++ ){
                int randomBuilding = new Random().nextInt(3)+1;
                String query = "INSERT INTO favlist (id_building, id_user) VALUES (" + randomBuilding + "," + i + ");";
                stmt.execute(query);
                int randomBuilding2 = 0;
                do{
                    randomBuilding2 = new Random().nextInt(3)+1;
                } while (randomBuilding == randomBuilding2);
                query = "INSERT INTO favlist (id_building, id_user) VALUES (" + randomBuilding2 + "," + i + ");";
                stmt.execute(query);
            }

            //Insert users
            String query = "INSERT INTO user (login, password, id_role) VALUES (\"magomed\", \"mycities\", 1);";
            stmt.execute(query);
            query = "INSERT INTO user (login, password, id_role) VALUES (\"frédéric\", \"mycities\", 1);";
            stmt.execute(query);
            query = "INSERT INTO user (login, password, id_role) VALUES (\"sebastien\", \"mycities\", 1);";
            stmt.execute(query);
            query = "INSERT INTO user (login, password, id_role) VALUES (\"utilisateur\", \"mycities\", 2);";
            stmt.execute(query);

            System.out.println("Tables inserted.");
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
