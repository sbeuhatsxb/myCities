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

    public static void deleteBuilding(Building building) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "DELETE FROM building WHERE id_building = "+building.getId()+";";
            System.out.println("Building" + building.getId() + " deleted");
            ResultSet rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public static boolean checkIfFavlistIsAlreadySet(int buildingId, int userId) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM favlist WHERE id_building = "+buildingId+" AND id_user = "+userId+";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public static List<Object> getFilteredStringByColumn(String table, String filter, String column) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM "+table+" WHERE "+column+" = \""+filter+"\";";

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
                            rs.getString("name"),
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
            String name,
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
        object.setName(name);
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
        object.setPassword(password);
        if(getFilteredIntByColumn(Env.ROLE, id_role).size() != 0 && !getFilteredIntByColumn(Env.ROLE, id_role).equals(null)){
            Role role = (Role) getFilteredIntByColumn(Env.ROLE, id_role).get(0);
            object.setRole(role);
        }

        return object;
    }

    public static void insertNewBuilding(Building building){
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

            String query = "INSERT INTO building (name, description, image, year, windows, id_city, id_architect, id_type, id_style, id_material, id_roof_type, id_frame)" +
                    " VALUES (\"" + name + "\", \"" + description + "\", \"" + image + "\", " + year + ", " + windows + ", " + idCity + ", " + idArchitect + ", " + idType + ", " + idStyle + ", " + idMaterial + ", " + idRoofType + ", " + idFrame + ");";
            stmt.execute(query);
            System.out.println("Building '" + building.getName() + "' created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertNewFavlist(Building building, User user){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            int buildingId = ((building.getId() == 0) ? 0 : building.getId());
            int userId = ((user.getId() == 0) ? 0 : user.getId());

            String query = "INSERT INTO favlist (id_building, id_user)" +
                    " VALUES (" + buildingId + ", " + userId + ");";
            stmt.execute(query);
            System.out.println("Favlist created for user "+ user.getLogin());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeFromFavlist(Building building, User user){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            int buildingId = ((building.getId() == 0) ? 0 : building.getId());
            int userId = ((user.getId() == 0) ? 0 : user.getId());
            String query = "DELETE FROM favlist WHERE id_building = "+buildingId+" AND id_user = "+userId+";";
            stmt.execute(query);
            System.out.println("Favlist created for user "+ user.getLogin());
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

        String[] countriesCapitals = {"Afrique du Sud", "Pretoria", "Afghanistan", "Kaboul", "Albanie", "Tirana", "Alg??rie", "Alger", "Allemagne", "Berlin", "Andorre", "Andorre-la-Vieille", "Angola", "Luanda", "Antigua-et-Barbuda", "Saint John&rsquo;s", "Arabie Saoudite", "Riyad", "Argentine", "Buenos Aires", "Arm??nie", "Erevan", "Australie", "Canberra", "Autriche", "Vienne", "Azerba??djan", "Bakou", "Bahamas", "Nassau", "Bahre??n", "Manama", "Bangladesh", "Dacca", "Barbade", "Bridgetown", "Belgique", "Bruxelles", "Belize", "Belmopan", "B??nin", "Porto-Novo", "Bhoutan", "Thimphou", "Bi??lorussie", "Minsk", "Birmanie", "Naypyidaw", "Bolivie", "Sucre", "Bosnie-Herz??govine", "Sarajevo", "Botswana", "Gaborone", "Br??sil", "Brasilia", "Brunei", "Bandar Seri Begawan", "Bulgarie", "Sofia", "Burkina Faso", "Ouagadougou", "Burundi", "Gitega", "Cambodge", "Phnom Penh", "Cameroun", "Yaound??", "Canada", "Ottawa", "Cap-Vert", "Praia", "Chili", "Santiago", "Chine", "P??kin", "Chypre", "Nicosie", "Colombie", "Bogota", "Comores", "Moroni", "Cor??e du Nord", "Pyongyang", "Cor??e du Sud", "S??oul", "Costa Rica", "San Jos??", "C??te d'Ivoire", "Yamoussoukro", "Croatie", "Zagreb", "Cuba", "La Havane", "Danemark", "Copenhague", "Djibouti", "Djibouti", "Dominique", "Roseau", "??gypte", "Le Caire", "??mirats arabes unis", "Abou Dabi", "??quateur", "Quito", "??rythr??e", "Asmara", "Espagne", "Madrid", "Eswatini", "Mbabane", "Estonie", "Tallinn", "??tats-Unis", "Washington", "??thiopie", "Addis-Abeba", "Fidji", "Suva", "Finlande", "Helsinki", "France", "Paris", "Gabon", "Libreville", "Gambie", "Banjul", "G??orgie", "Tbilissi", "Ghana", "Accra", "Gr??ce", "Ath??nes", "Grenade", "Saint-Georges", "Guatemala", "Guatemala", "Guin??e", "Conakry", "Guin??e ??quatoriale", "Malabo", "Guin??e-Bissau", "Bissau", "Guyana", "Georgetown", "Ha??ti", "Port-au-Prince", "Honduras", "Tegucigalpa", "Hongrie", "Budapest", "??les Cook", "Avarua", "??les Marshall", "Majuro", "Inde", "New Delhi", "Indon??sie", "Jakarta", "Irak", "Bagdad", "Iran", "T??h??ran", "Irlande", "Dublin", "Islande", "Reykjavik", "Isra??l", "J??rusalem", "Italie", "Rome", "Jama??que", "Kingston", "Japon", "Tokyo", "Jordanie", "Amman", "Kazakhstan", "Noursoultan", "Kenya", "Nairobi", "Kirghizistan", "Bichkek", "Kiribati", "Tarawa-Sud", "Kowe??t", "Kowe??t", "Laos", "Vientiane", "Lesotho", "Maseru", "Lettonie", "Riga", "Liban", "Beyrouth", "Liberia", "Monrovia", "Libye", "Tripoli", "Liechtenstein", "Vaduz", "Lituanie", "Vilnius", "Luxembourg", "Luxembourg", "Mac??doine", "Skopje", "Madagascar", "Antananarivo", "Malaisie", "Kuala Lumpur", "Malawi", "Lilongwe", "Maldives", "Mal??", "Mali", "Bamako", "Malte", "La Valette", "Maroc", "Rabat", "Maurice", "Port-Louis", "Mauritanie", "Nouakchott", "Mexique", "Mexico", "Micron??sie", "Palikir", "Moldavie", "Chi??in??u", "Monaco", "Monaco", "Mongolie", "Oulan-Bator", "Mont??n??gro", "Podgorica", "Mozambique", "Maputo", "Namibie", "Windhoek", "Nauru", "Yaren", "N??pal", "Katmandou", "Nicaragua", "Managua", "Niger", "Niamey", "Nigeria", "Abuja", "Niue", "Alofi", "Norv??ge", "Oslo", "Nouvelle-Z??lande", "Wellington", "Oman", "Mascate", "Ouganda", "Kampala", "Ouzb??kistan", "Tachkent", "Pakistan", "Islamabad", "Palaos", "Ngerulmud", "Palestine", "Ramallah", "Panama", "Panama", "Papouasie-Nouvelle-Guin??e", "Port Moresby", "Paraguay", "Asuncion", "Pays-Bas", "Amsterdam", "P??rou", "Lima", "Philippines", "Manille", "Pologne", "Varsovie", "Portugal", "Lisbonne", "Qatar", "Doha", "R??publique centrafricaine", "Bangui", "R??publique d??mocratique du Congo", "Kinshasa", "R??publique Dominicaine", "Saint-Domingue", "R??publique du Congo", "Brazzaville", "R??publique tch??que", "Prague", "Roumanie", "Bucarest", "Royaume-Uni", "Londres", "Russie", "Moscou", "Rwanda", "Kigali", "Saint-Kitts-et-Nevis", "Basseterre", "Saint-Vincent-et-les-Grenadines", "Kingstown", "Sainte-Lucie", "Castries", "Saint-Marin", "Saint-Marin", "Salomon", "Honiara", "Salvador", "San Salvador", "Samoa", "Apia", "S??o Tom??-et-Principe", "S??o Tom??", "S??n??gal", "Dakar", "Serbie", "Belgrade", "Seychelles", "Victoria", "Sierra Leone", "Freetown", "Singapour", "Singapour", "Slovaquie", "Bratislava", "Slov??nie", "Ljubljana", "Somalie", "Mogadiscio", "Soudan", "Khartoum", "Soudan du Sud", "Djouba", "Sri Lanka", "Sri Jayawardenapura", "Su??de", "Stockholm", "Suisse", "Berne", "Suriname", "Paramaribo", "Syrie", "Damas", "Tadjikistan", "Douchanb??", "Tanzanie", "Dodoma", "Tchad", "N&rsquo;Djam??na", "Tha??lande", "Bangkok", "Timor oriental", "Dili", "Togo", "Lom??", "Tonga", "Nuku??alofa", "Trinit??-et-Tobago", "Port-d&rsquo;Espagne", "Tunisie", "Tunis", "Turkm??nistan", "Achgabat", "Turquie", "Ankara", "Tuvalu", "Funafuti", "Ukraine", "Kiev", "Uruguay", "Montevideo", "Vanuatu", "Port-Vila", "Vatican", "Vatican", "Venezuela", "Caracas", "Vi??t Nam", "Hano??", "Y??men", "Sanaa", "Zambie", "Lusaka", "Zimbabwe", "Harare"};
        String[] styles = {"Architecture en b??ton", "Architectureenb??ton", "Architecture en bois", "Architectureenbois", "Architecture ottomane", "Architecture ottomane", "Architectureottomane", "Architectureparp??riode", "Architecture traditionnelle", "Architecture traditionnelle", "Architecturetraditionnelle", "Artd??co",
                "Art nouveau", "Architecture Art nouveau", "Architecture baroque", "Bauhaus", "Architecture brutaliste", "Architecture byzantine", "Architecture carolingienne", "Architecture classique", "Architecture coloniale",
                "Architecture constructiviste", "Architecture d??constructiviste", "Architecture ??clectique", "Architecture expressionniste",
                "Architecture futuriste", "Architecture gothique", "Gothique isab??lin", "Architecture high-tech", "Architecture industrielle", "Architecture mani??riste",
                "Style manu??lin", "Style architectural m??di??val", "Architecture m??taboliste", "Architecture m??tallique",
                "Architecture moderne", "Architecture mud??jare", "Architecture n??o-baroque", "Architecture n??o-byzantine",
                "Architecture n??oclassique", "Architecture n??o-gothique", "Architecture n??o-mauresque", "Architecture renaissance", "Architecture n??o-romane",
                "Architecture n??o-russe", "Architecture pal??ochr??tienne", "Postmodernisme (architecture)", "Architecture r??gionaliste",
                "Architecture romano-byzantine", "Architecture stalinienne", "Style international", "Style architectural tib??tain", "Architecture totalitaire",
                "Architecture victorienne"};

        String[] types = {"Structure d'accueil pour personnes ??g??es et personnes handicap??es","Salle d'audition, de conf??rences, de r??unions, de spectacles ou ?? usages multiples","Magasin de vente, Centres commerciaux","Restaurant et d??bit de boissons","H??tel et pension de familles","Salle de danse et salles de jeux","Etablissement d'enseignement","Biblioth??que, centre de documentation","Salle d'expositions","Etablissement de soin","Etablissement de culte","Administration, banques, bureaux","Etablissement sportif couvert","Mus??e","Etablissement de plein air","Chapiteau, tente et structure","Structure gonflable","Parc de stationnement couvert","Gare accessible au public","H??tel-restaurant d'altitude","Etablissement flottant","Refuge de montagne", "Palais", "Ob??lisque", "Capitole", "Cath??drale", "Chapelle", "Temple", "Forteresse", "Tour"};

        String[] roles = {"admin", "user"};
        String[] frames = {"bois", "m??tal"};
        String[] roofTypes = {
                "Toiture ?? 2 pans ou toiture ?? 2 versants",
                "Toiture ?? 4 pans ou toiture ?? 4 versants",
                "Toiture ?? demi-croupe",
                "Toiture ?? demi-croupe et brisis",
                "Toit terrasse ou toit plat",
                "Toit monopente",
                "Toiture papillon",
                "Toiture ?? la Mansart",
                "Comble am??nag?? avec lucarnes",
                "Toiture en dents de scie"
        };
        String[] materials = {"pierre", "pl??tre", "ciment", "chaux", "brique", "bloc", "pav??", "tuile", "ardoise", "carreau", "bardeau", "panneau de menuiserie", "produit ??tir??s", "verre ??tir??", "feuille d'acier", "feuille de plomb", "feuille de zinc", "membranes synth??tique", "produit lamin??s", "poutrelles", "barres", "fils en acier ou fer forg??"};

        String[] architects = {
                "Odorannus de Sens","Hugues, dit VGo","J","Jean le Bouteiller","S","Guillaume de Sens (architecte)","Bertrand de la Bacalaria","??tienne de Bonneuil","C","Jehan de Chelles","Renaud de Cormont","Thomas de Cormont","Robert de Coucy","D","Jean Deschamps (architecte)","E","Eudes de Montreuil","J","Jean-le-Loup","L","Hugues Libergier","Robert de Luzarches","M","Pierre de Montreuil","O","Jean d'Orbais","P","Pierre d'Angicourt","R","Gaucher de Reims","S","Jacques de Saint-Georges","Bernard de Soissons","V","Gautier de Varinfroy","Villard de Honnecourt","Pierre de C??bazat","Pierre de Chelles","Robert de Coucy","D","Drouet de Dammartin","Guy de Dammartin","F","Jacques de Fauran","J","Jacques de Beaujeu","L","Jean de Li??ge (architecte)","Sicard de Lordat","M","Mathieu d'Arras","N","Nicolas de Bonaventure","P","Pierre Perrat","R","Jean Ravy","S","Jacques de Saint-Georges","T","Raymond du Temple","Colin Biart","Jean Bornoy","C","Martin Chambiges","Nicolas Chantereine","D","Jean de Dammartin","G","Jean Gendrot","J","Jacques de Beaujeu","Jean de Commercy","L","Blaise L??cuyer","Roulland Le Roux","P","Guillaume Pontifs","R","Mathieu Reguaneau","Guillaume Robin","Mathurin Rodier","S","Jenson Salvart","T","Pierre Tarisel","V","Vauzy de Saint-Martin","Famille Androuet du Cerceau","Baptiste Androuet du Cerceau","Jacques Ier Androuet du Cerceau","Jacques II Androuet du Cerceau","B","Dominique Bachelier","Nicolas Bachelier","Jehan de Beauce","Colin Biart","Katherine Bri??onnet","Salomon de Brosse","Jean Bullant","C","Jean Martinet (architecte)","Jean Chambige","Martin Chambiges","Pierre Chambiges","Nicolas Chantereine","D","Jean Delespine (architecte)","Philibert Delorme","??tienne Dup??rac","F","Gabriel Favereau","Jehan de F??lin","G","Jean Goujon","H","Simon Hayneufve","J","Jacquette de Montbron","Jean de Beaujeu (architecte)","L","Gilles Le Breton","Nicolas Le Mercier","Roulland Le Roux","Pierre Lescot","M","??tienne Martellange","Cl??ment M??tezeau","Thibault M??tezeau","N","Bernard Nalot","Pierre Nepveu (architecte)","P","Guillaume Pelvoysin","R","Mathieu Reguaneau","Nicolas Ribonnier","S","Antoine Salvanh","Jean Salvanh","Hugues Sambin","Hector Sohier","Pierre Souffron","Jacques Sourdeau","V","Famille de la Vall??e","Jacques Aleaume","Famille Androuet du Cerceau","Jean Androuet du Cerceau","Augustin-Charles d'Aviler","B","Guillaume Bauduer","Fran??ois Blondel","Jean-Baptiste Bouchardon","Jacques Bougier","Jean-Baptiste Broebes","Jacques Bruant","Lib??ral Bruand","Jean-Baptiste Bullet de Chamblain","C","Bernard Campmartin","Tugal Caris","Isaac de Caus","Salomon de Caus","Charles Chamois","??tienne Corbineau","Gilles Corbineau","Jacques Corbineau","Pierre Corbineau","Pierre Cottard","Fr??min de Cotte","Robert de Cotte","Jacques Cubizol","D","Gabriel Dardaillon","Jean Delamonce","Nicolas II Delespine","Pierre Delisle-Mansart","Olivier Delourme","Fran??ois Derand","Girard Desargues","Antoine Desgodets","Julien Destr??e","Jean Dubois (sculpteur)","Guillaume Ducellet","Pierre Duplessy-Michel","E","Charles Errard","F","Andr?? F??libien","Jean-Fran??ois F??libien","G","Jacques Gabriel (mort en 1628)","Jacques Gabriel (1630-1686)","Jacques Gabriel (1605-1662)","Christophe Gamard","Germain Gaultier","Jacques Gentill??tre","Giral (architectes)","Daniel Gittard","Thomas Gobert (architecte)","Pierre Gole","H","Jules Hardouin-Mansart","Abraham Hardouin","Pierre Hardouin","J","Gilles Jodelet de La Boissi??re","L","Pierre Lambert (architecte)","Fran??ois Langlois (architecte)","Langlois (architectes)","Simon de la Vall??e","Nicolas Le Mercier","Pierre Le Muet","Philibert Le Roy","Fran??ois Le Vau","Louis Le Vau","Gabriel Le Duc","Pierre L??glise (ma??tre-ma??on)","Jean Lejuge","Jacques Lemercier","Michel Lemesle","Antoine Lepautre","Pierre Lepautre (1652-1716)","Pierre Levesville","M","Fran??ois Mansart","Daniel Marot","Jean Marot (architecte)","??tienne Martellange","Olivier Martinet","Jean-Baptiste Mathey","Simon Maupin","Cl??ment II M??tezeau","Louis M??tezeau","Pierre II Mignard","Pierre Miressou","N","Louis Noblet","O","Fran??ois Ier d'Orbay","Fran??ois II d'Orbay","P","Claude Pacot","Blaise Fran??ois Pagan","Jacques Peitret","Claude Perrault","Jean P??ru","Pierre Pommeyrol","Thomas Poussin","Pierre Puget","R","Jean-Claude Rambot","Nicolas Rambourg","Eustache Restout","Jean-Pierre Rivalz","Jonas Robelin","Marc Robelin","Fran??ois Romain","S","S??bastien de Saint-Aignan","Didier Sansonnet","Pierre Souffron","Gabriel Soulignac","T","Claude Terrin","V","Fran??ois de Royers de La Valfeni??re","Famille de la Vall??e","Marin de la Vall??e","Laurent Vallon","S??bastien Le Prestre de Vauban","Claude Vellefaux","Simon Vollant","Joseph Abeille","Antoine d'Allemand","Anatole Amoudru","Denis Antoine","Antoine-Charles Aubert","Jean Aubert (architecte)","Claude-Louis d'Aviler","B","Fran??ois Antoine Babuty-Desgodets","Claude Bacarit","Thomas Pierre Baraguey","Michel Bardoul de la Bigotti??re","Jean-Beno??t-Vincent Barr??","Fran??ois Dominique Barreau de Chefdeville","Jean Beausire","Jean-Baptiste Augustin Beausire","J??r??me Beausire","Fran??ois-Joseph B??langer","Claude Billard de B??lisard","J??r??me Charles Bellicard","Jean-Charles Bellu","Charles Joachim B??nard","Joseph B??nard","Pierre-Nicolas B??nard","Louis Benoist (architecte)","Louis-Martin Berthault","Jean-Baptiste Berthier","Claude Joseph Alexandre Bertrand","Charles-Jacques Billaudel","Jean-Ren?? Billaudel","Jacques-Fran??ois Blondel","Jean-Fran??ois Blondel","Joachim Bocher","Germain Boffrand","Pierre Bondon","Richard-Fran??ois Bonfin","Jacques-Charles Bonnard","Pierre Boscry","Jean-Baptiste Bouchardon","Juste-Nathan Fran??ois Boucher","Jean-Louis Bouet","??tienne-Louis Boull??e","Antoine Joseph de Bourge","Maximilien Br??bion","Louis Bretez","Charles-??tienne Briseux","Jean-Baptiste Broebes","Alexandre-Th??odore Brongniart","Joseph Brousseau","Ignace Fran??ois Broutin","Esprit-Joseph Brun","Jean-Ange Brun","Pierre-Gabriel Bugnet","Jean-Baptiste Bullet de Chamblain","C","Jean Cailleteau","Pierre Cailleteau","Fran??ois Cammas","Guillaume Cammas","Pierre Caqu??","Jean-Michel Carbonnar","Jean-Baptiste Caristie","Fran??ois Carlier (architecte)","Ren?? Carlier (architecte)","Jean-Sylvain Cartaud","Jean-Baptiste Ceineray","Jacques Cellerier","Jean-Fran??ois Chalgrin","Charles-Michel-Ange Challe","Jean-Baptiste Chaussard","Mathurin Cherpitel","Auguste Cheval de Saint-Hubert","Jean-Michel Chevotet","Antoine Choquet de Lindu","Charles Christiani","Claude Antoine Colombot","Charles-Louis Cl??risseau","Claude Cochet","Fran??ois Cointeraux","Louis-Marie Colignon","Jean-Baptiste Collet","Jean-Charles Colombot","Jean-Fran??ois Colson","Louis Combes (architecte)","Pierre Contant d'Ivry","Th??odore Cornut","Jules-Robert de Cotte","Louis de Cotte","Robert de Cotte","Jean Courtonne","Jean-Baptiste Courtonne","Charles-Pierre Coustou","Guillaume-Martin Couture","Joseph-Abel Couture","Mathurin Crucy","D","Victor-Thierry Dailly","Michel Dal Gabbio","Louis-Emmanuel-Aim?? Damesme","Jean Damun","Charles Fran??ois Darnaudin","Fran??ois-Antoine Davy de Chavign??","Fran??ois Debias-Aubry","Jean-Pierre Defrance","Jean Charles Delafosse","Claude-Mathieu Delagardette","Pierre-Alexis Delamair","Ferdinand-Sigismond Delamonce","Jean Delamonce","Fran??ois-Jacques Delannoy","Nicolas II Delespine","Pierre-Jules Delespine","Antoine Derizet","Antoine Desgodets","Claude Desgots","Pierre Desmaisons","Antoine-Victor Desmarais","Louis-Jean Desprez","Louis Devilliars","Claude Fran??ois Devosge","Charles De Wailly","Benjamin Joseph Dewarlez","Jacques Donnat","Julien-Fran??ois Douillard","Baltasar Dreveton","Nicolas Ducret","Jean-Baptiste Dufart","Alexandre Dufour (architecte)","L??on Dufourny","Nicolas Dulin","Gabriel Pierre Martin Dumont","Thomas Dumorey","Jean-Nicolas-Louis Durand","L??opold Durand","F","Jean-Fran??ois F??libien","Nicolas-Martial Foacier","Fran??ois Forestier de Villeneuve","Jacques Foucherot","Fran??ois Franque","Jean-Baptiste Franque","G","Ange-Antoine Gabriel","Ange-Jacques Gabriel","Jacques Gabriel (1667-1742)","Jean-Pierre Galezot","Bertrand Garipuy","Jean-Charles Garnier d'Isle","??miland Gauthey","Thomas Germain","Giral (architectes)","Nicolas-Claude Girardin","Alexandre Jean-Baptiste Guy de Gisors","Jacques-Pierre Gisors","Pierre Gittard","Pierre Fran??ois Godot","Georges-Claude Goiffon","Fran??ois-Joseph Gombert","Thomas-Joseph Gombert","Jacques Gondouin","Claude-Martin Goupy","Martin I Goupy","Martin II Goupy","Louis-Robert Goust","Antoine Groignard","Jacob Guerne","Fran??ois Gu??roult","Charles-Axel Guillaumot","Claude Guillot-Aubry","Barnab?? Guimard","H","Jacques Haneuse","Jacques Hardouin-Mansart de Sagonne","Jules Michel Alexandre Hardouin","Jean-Baptiste Philippe Harou","Michel-Barth??lemy Hazon","Pierre-Louis Helin","Jean-Fran??ois Heurtier","Houdault (architectes)","Fran??ois-Simon Houli??","Fran??ois Huguet","Jean Hupeau","Maximilien Joseph Hurtault","Jean-Jacques Huv??","I","Pierre-Michel d'Ixnard","J","Jean-Bernard-Abraham Jacquemin","Pierre Jacquot de Villeneuve","Jean-Nicolas Jadot de Ville-Issey","Claude Jean-Baptiste Jallier de Savault","Nicolas-Henri Jardin","Pierre-Sylvestre Jaunez","Barth??lemy Jeanson","Jean-Nicolas Jennesson","Denis Jossenay","K","Jean-Joseph Kapeller","L","Jean-Baptiste de La Rue","??loi Labarre","Hyacinthe de Labat de Savignac","??tienne Laclotte","Barth??l??my Lafon","Jacques de La Gu??pi??re","Philippe de la Gu??pi??re","Thomas Lain??e","Fran??ois Laurent Lamand??","Pierre Lambert (architecte)","Paul Abadie","Paul Abadie p??re","Jean-Marie Abgrall (historien)","Charles Abric","Gustave Alaux (architecte)","Gustave Alavoine","Jean-Antoine Alavoine","Camille Albert","Alfred-Philibert Aldrophe","Gaudensi Allar","Achille Ambialet","Gabriel-Auguste Ancelet","Charles Andr??","??mile Andr??","Gaspard Andr??","Louis-Jules Andr??","Pierre Andr?? (architecte)","Georges Antoine (homme politique)","Louis Henry Antoine","Albert Touzin","Germain Sauvanet","Alfred Armand","Charles Arnaud (architecte)","??douard Arnaud","Ferdinand Arnodin","Jean Jacques Nicolas Arveuf-Fransquin","Antoine-Charles Aubert","Pierre Aubl??","Alexandre ??mile Auburtin","Auguste-Henri Vildieu","Paul Auscher","??mile Auvray","B","Henry Bach","L??on Bachelin","Antoine-Nicolas Bailly","Ferdinand Bal","Achille Balli??re","Albert Ballu","Th??odore Ballu","Louis-Pierre Baltard","Victor Baltard","Charles-Louis Balzac","Thomas Pierre Baraguey","Andr?? de Baralle","Henri de Baralle","Julien Barbier","Louis Barbotin (architecte)","Charles Baron (architecte)","Ferdinand Joseph Hippolyte Barqui","Jacques-Eug??ne Barth??l??my","Edmond Auguste Bastien","Jules Batigny","Carlos Batteur","Anatole de Baudot","Ambroise Baudry","Jacques Baumier","Amand Louis Bauqu??","Fran??ois-Joseph B??langer","Louis Belin","Philibert Bellemain","??mile B??nard","Joseph B??nard","Pierre-Nicolas B??nard","B??nigne Claude Alfred Chevrot","Claude-Anthelme Benoit","L??on B??nouville","Pierre Louis B??nouville","Lucien Bentz","Charles Benvignat","??douard B??rard","Jean-Baptiste Bernard","Charles-Louis Bernier","Louis Bernier","Alfred Berruyer","Louis-Martin Berthault","Andr?? Berthier (architecte)","Arthur Bertin","Paul Bertrand (architecte)","Adolphe Berty","Hippolyte B??ziers-Lafosse","S??bastien-Marcel Biasini","Anatole Bienaim??","Georges Biet","Joseph Bigot","Charles Billor??","Ren?? Binet (architecte)","Jean-Prosper Bissuel","Prosper-??douard Bissuel","Victor-Auguste Blavette","??tienne Blon","Henri Blondel","Paul Blondel","Guillaume Abel Blouet","Auguste Bluysen","Prosper Bobin","Fran??ois-Adolphe Bocage","??mile Boeswillwald","Paul Boeswillwald","Louis-Auguste Boileau","Louis-Charles Boileau","??tienne-Joseph Boissonnade","Louis Boitte","Louis-Michel Boltz","Philippe-Alexandre-Louis Bommart","Richard-Fran??ois Bonfin","Jacques-Charles Bonnard","Louis Bonnier","Ernest Bosc","Pierre Bossan","Louis-Jules Bouchot","Pierre-Valentin Boudhors","Jean-Louis Bouet","Fran??ois Bougo??in","Auguste Louis ??douard Bouillon","Antoine-Fran??ois Bourbon","Jules Bourdais","Gaston Bourdon","Th??ophile Bourgeois","Gustave Bourgerel","Jules Bourgoin","Charles-D??sir?? Bourgon","Gustave Bourri??res","Jean Boussard","Maurice Boutterin","Joseph-Antoine Bouvard","Richard Bouwens van der Boijen","William Bouwens van der Boijen","Jean Br??asson","Gabriel Br??feil","Louis-Antoine-Maurice Bresson","Georges Le Breton","Alexandre-Th??odore Brongniart","Antoine Brossard","Emmanuel Brune","Marie-Joseph Brune","Narcisse Brunette","Ernest Brunnarius","Louis-Cl??mentin Bruyerre","Pierre-Gabriel Bugnet","Fernand Buisset","C","Jules Calbairac","F??lix Callet","Pierre Caloine","Camille Bodin-Legendre","Henri Cannissi??","Philippe Cannissi??","Auguste Caristie","Charles ??mile Carr??","Joseph Cassien-Bernard","Eug??ne-Toussaint Cateland","Louis Catoire","L??on Cayotte","Jacques Cellerier","Fran??ois-Alexis Cendrier","Pierre Chabat","Pierre Prosper Chabrol","Wilbrod Chabrol","Henri Chaine","Jean-Fran??ois Chalgrin","Alban Chambon","Alfred Chapon","Paul Charbonnier","Th??odore Charpentier","L??on Charvet","Charles Fr??d??ric Chass??riau","Charles-Fran??ois Chatelain","Andr?? Chatillon","Beno??t-Joseph Chatron","Jules Chatron","Fran??ois-Benjamin Chaussemiche","Joseph-Fleury Chenantais","Mathurin Cherpitel","Fran??ois-Auguste Cheussey","Jean-Baptiste Chevalt","Antoine-Marie Chenavard","Charles Chipiez","Antonin Chomel","Augustin Chomel","Fran??ois Clasquin","Claude Antoine Colombot","Gustave Clausse","Charles-Louis Cl??risseau","Pierre Clochar","Georges Closson","Joseph Clugnet","Claude Cochet","Fran??ois Cointeraux","Jean Geoffroy Conrath","Simon-Claude Constant-Dufeux","Henri Contamine","Georges-Ernest Coquart","Adolphe Coquet","Jean Baptiste Cordonnier","Louis Marie Cordonnier","??douard Corroyer","Pascal Coste","L??once Cou??toux","Octave Courtois-Suffit","??lie Courtonne","Claude-Philippe Cramail","Charles Abella","Charles Adda","Henri Ad??","Georges Adilon","Jacques Adnet","Jean-Paul Alaux","Dominique Alba","Georges Albenque","Camille Albert","Alexandre Fournier","Philippe Ameller","Michel Andrault","Charles Andr??","??mile Andr??","Gaspard Andr??","Jacques et Michel Andr??","Pierre Andr?? (architecte)","Paul Andreu","Pierre Ansart (architecte)","Andr?? Arbus","Architectes des palais de Nice","Andr?? Arfvidson","Henri Armbruster (architecte)","L??on Arnal","Charles Arnaud (architecte)","??douard Arnaud","Charles Arnault (1890-1950)","Ferdinand Arnodin","Arthur Chaudouet","Jules-Godefroy Astruc","Pierre Aubl??","Louis Aublet","Fran??ois Aubry (architecte)","Jacques Marcel Auburtin","Victor Auclair","Alfred Audoul","Roger Aujame","Paul Auscher","??douard Autant","??mile Auvray","B","L??on Bachelin","??ric Bagge","Georges-Eug??ne Balleyguier","Albert Ballu","Raymond Barbaud","Pierre Barbe","Julien Barbier","Henri Bard","Renaud Bardon","Carlos Batteur","Anatole de Baudot","??douard Bauhain","Yves Bayard","G??rard Beau de Lom??nie","Henri Beauclair (architecte)","Jean Beaugrand","Emmanuel Bellini","Dom Bellot","??mile B??nard","Lucien Bentz","Andr?? B??rard (architecte)","??douard B??rard","Patrick Berger (architecte)","Ren??-F??lix Berger","Henry Bernard","Louis Bernier","Jack Berthelot","Marc Berthier","Arthur Bertin","??mile Bertone","Paul Bertrand (architecte)","L??on Besnard","Charles-Henri Besnard","L??on B??nard","Ars??ne Bical","Anatole Bienaim??","Georges Biet","Paul Bigot","Philippe Bigot (architecte)","Juliette Billard","Ren?? Binet (architecte)","Prosper-??douard Bissuel","Maurice Blanc (architecte)","Jean-Fran??ois Blassel","Victor-Auguste Blavette","Andr?? Bloc","Henri-L??on Bloch","Auguste Bluysen","Prosper Bobin","Fran??ois-Adolphe Bocage","Jean-Fran??ois Bodin (architecte)","Paul Boeswillwald","Xavier Bohl","Camille Boignard","Louis-Hippolyte Boileau","Pierre Boille","Yves Boiret","Guillaume du Boisbaudry","Jean Boissel","Andr?? Boll","Patrice Bonnet","Louis Bonnier","Philippe Bonnin (architecte)","David Bonpain","Dariush Borbor","Pierre Bourdeix","Fr??d??ric Borel","Auguste Bossu","Jean Bossu (architecte)","Patrick Bouchain","Jean Boucher (architecte)","Andr?? Boucton","Marcel Boudin","Mohamed Boudjedra","Philippe Boudon","Jean Boudriot","Bernard Bougeault","Florence Bougnoux","Joseph Bougo??in","Paul Bougo??in","James Bouill??","Olivier Bouill??re","Fernand Boukobza","Antoine-Fran??ois Bourbon","Alain Bourbonnais","Charles Bourgeois (architecte)","Th??ophile Bourgeois","Charles-D??sir?? Bourgon","Pierre Bourineau","??mile Boutin (peintre)","Maurice Boutterin","Joseph-Antoine Bouvard","Roger Bouvard","Richard Bouwens van der Boijen","Marius Boyer","Louis Brachet","Raoul Brandon","Louis Brassart-Mariage","Reinhart Braun","Albert-Louis Bray","Jean Br??asson","Georges Le Breton","Marc Brillaud de Laujardi??re","Edmond Brion","Yves Brunier (paysagiste)","Raoul Brygoo","Iwona Buczkowska","Jean-Pierre Buffi","Fernand Buisset","Joseph Bukiet","Raymond Busse","R??my Butler","C","Christian Cacaut","Olivier-Cl??ment Cacoub","Auguste Cadet","Robert Cado","Jules Calbairac","Henri-Jean Calsat","Albert Caquot","William Cargill","Pierre-Louis Carlier","Jacques Carlu","Fran??ois Carpentier","Joseph Carr??","Louis de Casabianca","Bernard Casnin","Urbain Cassan","Joseph Cassien-Bernard","Gaston Castel","Roland Castro (architecte)","Am??d??e Cateland","Emmanuel Cateland","Jean Cateland","Louis Cauvin (architecte)","C??sar Cavallin","L??on Cayotte","Patrick C??leste","Wilbrod Chabrol","G??rard Chamayou","Alban Chambon","Jean-Louis Chan??ac","Marcel Chaney","Marcel Chappey","Laurent Chappis","Paul Charbonnier","Pierre Chareau","Charles Javelle","Claude Charpentier","Jean-Marie Charpentier","L??on Charvet","Andr?? Chatelin","Fran??ois Chatillon (architecte)","Utilisateur:Xcvbn,gfds/Brouillon","Eug??ne Chauliat","Olivia Chaumont","Fran??ois-Benjamin Chaussemiche","Charles Chaussepied","Andr?? Chauvet","Georges Chedanne","Andr?? Cheftel","Delphine Aboulker","Dominique Alba","Philippe Ameller","Paul Andreu","Silvio d'Ascia","Roger Aujame","Jean Fran??ois Authier","B","Yves Ballot","Marc Barani","Yves Bayard","Alun Be","Patrick Berger (architecte)","Marc Berthier","Christian Biecher","Gr??goire Bignier","Cl??ment Blanchet","Jean-Fran??ois Blassel","Jean-Fran??ois Bodin (architecte)","Xavier Bohl","Yves Boiret","Philippe Bonnin (architecte)","Fr??d??ric Borel","Patrick Bouchain","Philippe Boudon","Florence Bougnoux","Olivier Bouill??re","Iwona Buczkowska","Jean-Pierre Buffi","R??my Butler","C","Pierre-Louis Carlier","Roland Castro (architecte)","Patrick C??leste","Fran??ois Chatillon (architecte)","Utilisateur:Xcvbn,gfds/Brouillon","Olivia Chaumont","Alexandre Chemetoff","Paul Chemetov","Christophe Cheron","Jean Chollet (sc??nographe)","Olivier Cinqualbre","Henri Ciriani","Henri Colombani","Vincen Cornu","Patrick Coulombel","Didier Courbot","Pascal Cribier","D","Sophie Dabat","Christine Dalnoky","Odile Decq","Luc Delemazure","Anne D??mians","Philippe Demougeot","Fran??ois Depresle","Jean-Paul Desbat","V??ronique Descharri??res","Fran??ois Deslaugiers","Bernard Desmoulin","Thierry Despont","Christian Devillers","Borhene Dhaouadi","Sirandou Diawara","Fr??d??ric Didier","Joseph Dirand","Christian Drevet","Fr??d??ric Druot","Sylvain Dubuisson","Laurent Duport","Jean-Marie Duthilleul","E","Gilles Ebersolt","Luce Eekman","F","Adrien Fainsilber","Pierre-Louis Faloci","Didier Faustino","Pierre Ferret (1949)","Jacques Ferrier","Stanislas Fiszer","Bruno Fortier","Philippe Fraleu","??douard Fran??ois","Franklin Azzi","Fr??d??ric Bonnet","Fran??oise Fromonot","Catherine Furet","G","Jean de Gastines","Pierre-Antoine Gatier","Bruno Gaudin","Henri Gaudin","Manuelle Gautrand","Philippe Gazeau","Lina Ghotmeh","Victor Gingembre","??dith Girard","Charles Goldblum","G??rard Grandval","Fran??ois Grether","Antoine Grumbach","Pascale Gu??dot","Claire Guieysse","H","Franck Hammout??ne","Christian Hauvette","Jean-Marie Hennin","Bruno Huerre","I","Jean-Marc Ibos et Myrto Vitart","J","Catherine Jacquot","Jean-Fran??ois Jodry","V??ronique Joffre (architecte)","Fran??oise-H??l??ne Jourda","K","Daniel Kahane","Kardham Cardete Huet Architecture","Fr??d??ric Keiff","Mahmoud Keldi","L","Christophe Lab","Julien Labrousse","Anne Lacaton","Denis Laming","Didier Laroque","Ga??lle Lauriot-Pr??vost","Thomas Lavigne","Jacques-??mile Lecaron","Fran??ois Leclercq","Bertrand Lemoine","Sylvain Lhermitte","Christian Liaigre","Yves Lion","Jacques Lucan","Dominique Lyon","M","Philippe Madec","India Mahdavi","Arthur Mamou-Mani","David Mangin","Didier Maufras","Fiona Meadows","Thierry Melot","Brigitte M??tra","Nicolas Michelin","Marc Mimram","Marine Miroux","Alain Moatti","Pascal Morabito","Jean-Paul Morel","Michel Mossessian","Jacques Moulin (architecte)","N","Nicolas Normier","Jean Nouvel","O","Olivier Saguez","P","Philippe Panerai","Ephraim Henry Pavie","Jean-Michel Payet","Utilisateur:De gorostarzu Patricia/Fabrice Peltier","Georges Pencreac'h","Ga??lle P??neau","Marc Perelman","Gilles Perraudin","Dominique Perrault","Alain-Charles Perrot","William Pesson","Jean-Paul Philippon","Antoine Picon","Jean Pistre","Jean-Loup Pivin","Christian de Portzamparc","Elizabeth de Portzamparc","Philippe Prost","Allain Provost","R","Reichen et Robert","Michel R??mon","Rudy Ricciotti","Olivier Rigaud","Jacques Ripault","Adeline Rispal","Antoinette Robain","Marc Rolinet","Pascal Rollet","Jean-Loup Roubert","Jacques Rougerie (architecte)","Fr??d??ric Ruyant","S","Serge Salat","Alain Sarfati (architecte)","Maurice Sauzet","Fran??ois Scali","Christiane Schmuckle-Mollard","Richard Scoffier","Fran??ois Seigneur","David Serero (architecte)","Lotfi Sidirahal","Jacques Simon (paysagiste)","Francis Soler","Nicolas Soulier","Isabelle Stanislas","Philippe Starck","Antoine Stinco","T","Jad Tabet","Roger Taillibert","Annette Tison","Herv?? Tordjman","Daniel Treiber","V","Marc Van Peteghem","Jean-Philippe Vassal","Jean-Yves Veillard","Michel Vernes"
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
            query = "INSERT INTO user (login, password, id_role) VALUES (\"fr??d??ric\", \"mycities\", 1);";
            stmt.execute(query);
            query = "INSERT INTO user (login, password, id_role) VALUES (\"sebastien\", \"mycities\", 1);";
            stmt.execute(query);
            query = "INSERT INTO user (login, password, id_role) VALUES (\"utilisateur\", \"mycities\", 2);";
            stmt.execute(query);

            //Insert buildings
            BuildingBuilder building1 = new BuildingBuilder("Le ch??teau de Charlottenbourg",0,1713,5,1,1,23,1,15,"Le ch??teau de Charlottenbourg, est un ch??teau situ?? dans le quartier de Charlottenbourg ?? Berlin. C'est la plus ancienne r??sidence des Hohenzollern et le plus grand palais de Berlin.","1.jpg", getRandom(1000));
            BuildingBuilder building2 = new BuildingBuilder("Le palais du Reichstag",0,1884,5,1,1,23,5,40,"Le palais du Reichstag est un b??timent de Berlin en Allemagne, construit pour abriter le Reichstag ?? partir de 1894 et jusqu'?? son incendie dans la nuit du 27 au 28 f??vrier 1933. Il abrite le Bundestag de la R??publique f??d??ralec d'Allemagne depuis le retour des institutions ?? Berlin en 1999.","2.jpg", getRandom(1000));
            BuildingBuilder building3 = new BuildingBuilder("Mus??e de Pergame",0,1910,5,1,1,14,5,20,"Le mus??e de Pergame est un mus??e arch??ologique de Berlin, situ?? sur l'??le aux Mus??es. Le mus??e propose une collection d???antiquit??s, un d??partement du Proche-Orient et un mus??e de l'Art islamique. Il est connu pour ses reconstitutions d'??uvres monumentales comme la porte d'Ishtar, le grand autel de Pergame, la porte du march?? de Milet et la fa??ade du palais de Mchatta.","3.jpg", getRandom(1000));
            BuildingBuilder building4 = new BuildingBuilder("Mus??e du Prado",0,1819,55,1,1,14,5,40,"Le mus??e du Prado ?? Madrid est l'une des plus grandes et des plus importantes pinacoth??ques du monde. Il pr??sente principalement des peintures europ??ennes du XIV??? si??cle au d??but du XIX??? si??cle, collectionn??es par les Habsbourg et les Bourbons.","4.jpg", getRandom(1000));
            BuildingBuilder building5 = new BuildingBuilder("Mus??e national Thyssen-Bornemisza",0,1992,55,2,3,14,5,40,"Le mus??e national Thyssen-Bornemisza est un mus??e d'art ancien, moderne et contemporain situ?? ?? Madrid. Son origine tient dans l'acquisition qu'a faite le gouvernement espagnol, en juillet 1993, de la majeure partie de la collection d'art r??unie, ?? Lugano, par la famille Thyssen-Bornemisza, en compl??ment des pinacoth??ques et des collections nationales d??j?? existantes.","5.jpg", getRandom(1000));
            BuildingBuilder building6 = new BuildingBuilder("Palais royal de Madrid",0,1764,55,1,1,23,5,15,"Le palais royal de Madrid est la r??sidence officielle du roi d'Espagne. Les rois actuels ne r??sident pas en son sein, mais plut??t au palais de la Zarzuela. Le palais royal est utilis?? pour des fonctions protocolaires. Avec une superficie de 135 000 m?? et 3 418 pi??ces, c'est le plus grand palais royal d'Europe occidentale et l'un des plus grands au monde.","6.jpg", getRandom(1000));
            BuildingBuilder building7 = new BuildingBuilder("Capitole des EU",0,1812,58,2,1,25,5,40,"Le Capitole des ??tats-Unis est le b??timent qui sert de si??ge au Congr??s, le pouvoir l??gislatif des ??tats-Unis. Il est situ?? dans la capitale f??d??rale, Washington, D.C.","7.jpg", getRandom(1000));
            BuildingBuilder building8 = new BuildingBuilder("National Gallery of Art",0,1937,58,2,1,14,5,40,"La National Gallery of Art, est un mus??e am??ricain situ?? ?? Washington, l'un des plus grands du monde. Il est administr?? par le gouvernement f??d??ral des ??tats-Unis et son entr??e est gratuite. Les collections sont r??parties dans deux b??timents reli??s par une galerie souterraine sous le Mall : le b??timent Est et le b??timent Ouest. La NGA est affili??e ?? la Smithsonian Institution.","8.jpg", getRandom(1000));
            BuildingBuilder building9 = new BuildingBuilder("Washington Monument",0,1884,58,2,1,24,5,24,"Le Washington Monument est un ob??lisque de plus de 169 m??tres de haut, inaugur?? le 21 f??vrier 1885 en l'honneur de George Washington, le premier pr??sident des ??tats-Unis, et situ?? ?? Washington, D.C. Fait de marbre, de gr??s et de granit, il est construit en ma??onnerie.","9.jpg", getRandom(1000));
            BuildingBuilder building10 = new BuildingBuilder("La tour Eiffel",0,1889,62,2,21,30,0,35,"La tour Eiffel est une tour de fer puddl?? de 324 m??tres de hauteur situ??e ?? Paris, ?? l'extr??mit?? nord-ouest du parc du Champ-de-Mars en bordure de la Seine dans le 7??? arrondissement. Son adresse officielle est 5, avenue Anatole-France.touristique de premier plan : il s'agit du troisi??me site culturel fran??ais payant le plus visit?? en 2015, avec 5,9 millions de visiteurs en 2016.","10.jpg", getRandom(1000));
            BuildingBuilder building11 = new BuildingBuilder("La cath??drale Notre-Dame",0,1163,62,1,1,26,2,27,"La cath??drale Notre-Dame de Paris, commun??ment appel??e Notre-Dame, est l'un des monuments les plus embl??matiques de Paris et de la France. Elle est situ??e sur l'??le de la Cit?? et est un lieu de culte catholique, si??ge de l'archidioc??se de Paris, d??di??e ?? la Vierge Marie.","11.jpg", getRandom(1000));
            BuildingBuilder building12 = new BuildingBuilder("Le ch??teau de Versailles",0,2017,62,1,1,23,2,20,"Le ch??teau de Versailles est un ch??teau et un monument historique fran??ais qui se situe ?? Versailles, dans les Yvelines, en France. Il fut la r??sidence des rois de France Louis XIV, Louis XV et Louis XVI. Le roi et la cour y r??sid??rent de fa??on permanente du 6 mai 1682 au 6 octobre 1789, ?? l'exception des ann??es de la R??gence de 1715 ?? 1723. Situ??s au sud-ouest de Paris, ce ch??teau et son domaine visaient ?? glorifier la monarchie fran??aise","12.jpg", getRandom(1000));
            BuildingBuilder building13 = new BuildingBuilder("La basilique Saint-Pierre",0,1626,86,1,1,11,2,43,"La basilique Saint-Pierre est le plus important ??difice religieux du catholicisme. Elle est situ??e au Vatican, sur la rive droite du Tibre, et sa fa??ade s'ouvre sur la place Saint-Pierre. Elle a ??t?? construite l?? o??, sous la volont?? de l'empereur Constantin I?????, les premiers p??lerins venaient rendre un culte ?? saint Pierre ?? l'emplacement du cirque de Caligula et de N??ron.","13.jpg", getRandom(1000));
            BuildingBuilder building14 = new BuildingBuilder("La chapelle Sixtine",0,1483,86,1,1,27,2,43,"La chapelle Sixtine, appel??e originellement chapelle de Sixte, est une des salles des palais pontificaux du Vatican et fait partie des Mus??es du Vatican. Rempla??ant la chapelle Pauline puis le palais du Quirinal, la chapelle Sixtine est le lieu o??, traditionnellement depuis le XV??? si??cle, les cardinaux r??unis en conclave ??lisent le nouveau pape, et obligatoirement depuis la constitution apostolique ??dict??e par Jean-Paul II en 1996, intitul??e Universi Dominici gregis. La plus grande chapelle du Vatican doit son nom au pape Sixte IV, qui la fit b??tir de 1477 ?? 1483.","14.jpg", getRandom(1000));
            BuildingBuilder building15 = new BuildingBuilder("Le Panth??on",0,125,86,1,1,28,0,49,"Le Panth??on de Rome est un ??difice religieux antique situ?? sur la piazza della Rotonda (Rome), b??ti sur l'ordre d'Agrippa au I????? si??cle av. J.-C. Endommag?? par plusieurs incendies, il fut enti??rement reconstruit sous Hadrien. ?? l'origine, le Panth??on ??tait un temple d??di?? ?? toutes les divinit??s de la religion antique. ","15.jpg", getRandom(1000));
            BuildingBuilder building16 = new BuildingBuilder("British Museum",0,1759,151,1,1,14,2,40,"Le British Museum, est un mus??e de l'histoire et de la culture humaine, situ?? ?? Londres, au Royaume-Uni. Ses collections, constitu??es de plus de sept millions d'objets, sont parmi les plus importantes du monde et proviennent de tous les continents. Elles illustrent l'histoire humaine de ses d??buts ?? aujourd'hui. Le mus??e a ??t?? fond?? en 1753 et ouvert au public en 1759.","16.jpg", getRandom(1000));
            BuildingBuilder building17 = new BuildingBuilder("La cath??drale Saint-Paul",0,1710,151,1,1,26,2,20,"La cath??drale Saint-Paul de Londres est la cath??drale du dioc??se de Londres de l'??glise d'Angleterre. Elle a ??t?? construite apr??s la destruction de l'ancien ??difice lors du grand incendie de Londres de 1666. Elle couronne Ludgate Hill, site qui accueillit quatre sanctuaires avant la cath??drale actuelle et se trouve dans la Cit?? de Londres, c??ur historique de la ville devenu aujourd'hui le principal quartier d'affaires londonien.","17.jpg", getRandom(1000));
            BuildingBuilder building18 = new BuildingBuilder("La tour de Londres",0,1285,151,1,1,29,2,41,"La tour de Londres, en anglais Tower of London est une forteresse historique situ??e sur la rive nord de la Tamise ?? Londres en Angleterre ?? c??t?? du Tower Bridge. La tour se trouve dans le borough londonien de Tower Hamlets situ?? ?? l'est de la Cit?? de Londres dans un espace appel?? Tower Hill. Sa construction commen??a vers la fin de l'ann??e 1066 dans le cadre de la conqu??te normande de l'Angleterre.","18.jpg", getRandom(1000));

            List<BuildingBuilder> buildingBuilderList = new ArrayList<>();

            buildingBuilderList.add(building1);
            buildingBuilderList.add(building2);
            buildingBuilderList.add(building3);
            buildingBuilderList.add(building4);
            buildingBuilderList.add(building5);
            buildingBuilderList.add(building6);
            buildingBuilderList.add(building7);
            buildingBuilderList.add(building8);
            buildingBuilderList.add(building9);
            buildingBuilderList.add(building10);
            buildingBuilderList.add(building11);
            buildingBuilderList.add(building12);
            buildingBuilderList.add(building13);
            buildingBuilderList.add(building14);
            buildingBuilderList.add(building15);
            buildingBuilderList.add(building16);
            buildingBuilderList.add(building17);
            buildingBuilderList.add(building18);


            for( BuildingBuilder building : buildingBuilderList){
                String name = building.getName();
                String description = building.getDescription();
                String image = building.getImage();
                int year = building.getYear();
                int windows = building.getWindows();
                int idCity = building.getCity();
                int idStyle = building.getStyle();
                int idType = building.getType();
                int idMaterial = building.getMaterial();
                int idRoof = building.getRoofType();
                int idFrame = building.getFrame();
                int idArchitect = building.getArchitect();

                query = "INSERT INTO building (" +
                        "name, " +
                        "windows, " +
                        "year, " +
                        "id_city, " +
                        "id_frame, " +
                        "id_material, " +
                        "id_type, " +
                        "id_roof_type, " +
                        "id_style, " +
                        "id_architect, " +
                        "description, " +
                        "image) " +
                        "VALUES (" +
                        "\""+name+"\", " +
                        ""+windows+", " +
                        ""+year+",  " +
                        ""+idCity+", " +
                        ""+idFrame+", " +
                        ""+idMaterial+", " +
                        ""+idType+", " +
                        ""+idRoof+", " +
                        ""+idStyle+", " +
                        ""+idArchitect+", " +
                        "\""+description+"\", " +
                        "\""+image+"\");";

                stmt.execute(query);
            }

            System.out.println("Tables inserted.");
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getRandom(int number){
        return new Random().nextInt(number)+1;
    }
}
