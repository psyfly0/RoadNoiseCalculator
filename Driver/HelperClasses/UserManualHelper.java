/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.HelperClasses;

import java.util.HashMap;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author szaboa
 */
/**
 * Utility class for managing the user manual data.
 */
public class UserManualHelper {
    
    /**
    * Creates the chapter data for the user manual tree.
    *
    * @return the root node containing the chapter data
    */
    public static DefaultMutableTreeNode createChapterData() {
        DefaultMutableTreeNode rootNode = 
                new DefaultMutableTreeNode("Használati útmutató");
        
        // Create the chapters
        DefaultMutableTreeNode chapter0Node = 
                new DefaultMutableTreeNode("Általános leírás");
        DefaultMutableTreeNode chapter1Node = 
                new DefaultMutableTreeNode("Fájl menü");
        DefaultMutableTreeNode chapter2Node = 
                new DefaultMutableTreeNode("Mûveletek menü");
        DefaultMutableTreeNode chapter3Node = 
                new DefaultMutableTreeNode("Rendezés / Különbség menü");

        // Create the subsections for Chapter 0
        DefaultMutableTreeNode subsection0_1_Node = 
                new DefaultMutableTreeNode("Általános információ");
        DefaultMutableTreeNode subsection0_2_Node = 
                new DefaultMutableTreeNode("Használat általános leírása");

        chapter0Node.add(subsection0_1_Node);
        chapter0Node.add(subsection0_2_Node);

        // Create the subsections for Chapter 1
        DefaultMutableTreeNode subsection1_1_Node = 
                new DefaultMutableTreeNode("Megnyitás");
        DefaultMutableTreeNode subsection1_2_Node = 
                new DefaultMutableTreeNode("Mentés");
        DefaultMutableTreeNode subsection1_3_Node = 
                new DefaultMutableTreeNode("Aktuális projekt bezárása");
        DefaultMutableTreeNode subsection1_4_Node = 
                new DefaultMutableTreeNode("Minden projekt bezárása");
        DefaultMutableTreeNode subsection1_5_Node = 
                new DefaultMutableTreeNode("Kilépés");
        chapter1Node.add(subsection1_1_Node);
        chapter1Node.add(subsection1_2_Node);
        chapter1Node.add(subsection1_3_Node);
        chapter1Node.add(subsection1_4_Node);
        chapter1Node.add(subsection1_5_Node);

        // Create the subsections for Chapter 2
        DefaultMutableTreeNode subsection2_1_Node = 
                new DefaultMutableTreeNode("Számítási Mûveletek");
        DefaultMutableTreeNode subsection2_1_1_Node = 
                new DefaultMutableTreeNode("Számítás minden értékre");
        DefaultMutableTreeNode subsection2_1_2_Node = 
                new DefaultMutableTreeNode("Egyenértékû A-hangnyomásszint számítása");
        DefaultMutableTreeNode subsection2_1_3_Node = 
                new DefaultMutableTreeNode("Hangteljesítményszint számítása");
        DefaultMutableTreeNode subsection2_1_4_Node = 
                new DefaultMutableTreeNode("Védõtávolság számítása");
        DefaultMutableTreeNode subsection2_1_5_Node = 
                new DefaultMutableTreeNode("Hatásterület számítása");
        DefaultMutableTreeNode subsection2_1_6_Node = 
                new DefaultMutableTreeNode("Adott távolságon adódó zajterhelés");

        DefaultMutableTreeNode subsection2_2_Node = 
                new DefaultMutableTreeNode("Oszlopok törlése");
        DefaultMutableTreeNode subsection2_3_Node = 
                new DefaultMutableTreeNode("Védõtávolság mentése");
        DefaultMutableTreeNode subsection2_4_Node = 
                new DefaultMutableTreeNode("Hatásterület mentése");

        subsection2_1_Node.add(subsection2_1_1_Node);
        subsection2_1_Node.add(subsection2_1_2_Node);
        subsection2_1_Node.add(subsection2_1_3_Node);
        subsection2_1_Node.add(subsection2_1_4_Node);
        subsection2_1_Node.add(subsection2_1_5_Node);
        subsection2_1_Node.add(subsection2_1_6_Node);

        chapter2Node.add(subsection2_1_Node);
        chapter2Node.add(subsection2_2_Node);
        chapter2Node.add(subsection2_3_Node);
        chapter2Node.add(subsection2_4_Node);

        // Create the subsections for Chapter 3
        DefaultMutableTreeNode subsection3_1_Node = 
                new DefaultMutableTreeNode("Rendezés");
        DefaultMutableTreeNode subsection3_1_1_Node = 
                new DefaultMutableTreeNode("...nappali LAeq alapján");
        DefaultMutableTreeNode subsection3_1_2_Node = 
                new DefaultMutableTreeNode("...éjjeli LAeq alapján");
        DefaultMutableTreeNode subsection3_1_3_Node = 
                new DefaultMutableTreeNode("...nappali védõtávolság alapján");
        DefaultMutableTreeNode subsection3_1_4_Node = 
                new DefaultMutableTreeNode("...éjjeli védõtávolság alapján");
        DefaultMutableTreeNode subsection3_1_5_Node = 
                new DefaultMutableTreeNode("...nappali hatásterület alapján");
        DefaultMutableTreeNode subsection3_1_6_Node = 
                new DefaultMutableTreeNode("...éjjeli hatásterület alapján");
        DefaultMutableTreeNode subsection3_1_7_Node = 
                new DefaultMutableTreeNode("Nappali LAeq különbségek rendezése");
        DefaultMutableTreeNode subsection3_1_8_Node = 
                new DefaultMutableTreeNode("Éjjeli LAeq különbségek rendezése");
        DefaultMutableTreeNode subsection3_1_9_Node = 
                new DefaultMutableTreeNode("Eredeti sorrend visszaállítása");

        DefaultMutableTreeNode subsection3_2_Node = 
                new DefaultMutableTreeNode("Állapotok különbsége");

        subsection3_1_Node.add(subsection3_1_1_Node);
        subsection3_1_Node.add(subsection3_1_2_Node);
        subsection3_1_Node.add(subsection3_1_3_Node);
        subsection3_1_Node.add(subsection3_1_4_Node);
        subsection3_1_Node.add(subsection3_1_5_Node);
        subsection3_1_Node.add(subsection3_1_6_Node);
        subsection3_1_Node.add(subsection3_1_7_Node);
        subsection3_1_Node.add(subsection3_1_8_Node);
        subsection3_1_Node.add(subsection3_1_9_Node);

        chapter3Node.add(subsection3_1_Node);
        chapter3Node.add(subsection3_2_Node); 

        // Add the chapters to the root node
        rootNode.add(chapter0Node);
        rootNode.add(chapter1Node);
        rootNode.add(chapter2Node);
        rootNode.add(chapter3Node);

        return rootNode;
    }  
    
    /**
    * Creates and returns a map of chapter texts.
    *
    * @return A map containing the chapter texts, where the key is the chapter name and the value is the corresponding text.
    */
    public static Map<String, String> createChapterTexts() {
        Map<String, String> chapterTexts = new HashMap<>();

        // Chapter 0 texts
        chapterTexts.put("Általános információ", altalanosInfo());
        chapterTexts.put("Használat általános leírása", altalanosHasznalat());

        // Chapter 1 texts
        chapterTexts.put("Megnyitás", megnyitas());
        chapterTexts.put("Mentés", mentes());
        chapterTexts.put("Aktuális projekt bezárása", aktualisBezar());
        chapterTexts.put("Minden projekt bezárása", mindentBezar());
        chapterTexts.put("Kilépés", kilep());

        // Chapter 2 texts
        chapterTexts.put("Számítás minden értékre", szamitMind());
        chapterTexts.put("Egyenértékû A-hangnyomásszint számítása", szamitLAeq());
        chapterTexts.put("Hangteljesítményszint számítása", szamitLw());
        chapterTexts.put("Védõtávolság számítása", szamitVedo());
        chapterTexts.put("Hatásterület számítása", szamitHatas());
        chapterTexts.put("Adott távolságon adódó zajterhelés", szamitAdottTavolsag());
        chapterTexts.put("Oszlopok törlése", oszlopTorol());
        chapterTexts.put("Védõtávolság mentése", vedoMentes());
        chapterTexts.put("Hatásterület mentése", hatasMentes());

        // Chapter 3 texts
        chapterTexts.put("...nappali LAeq alapján", rendezNappalLaeq());
        chapterTexts.put("...éjjeli LAeq alapján", rendezEjjelLaeq());
        chapterTexts.put("...nappali védõtávolság alapján", rendezNappalVedo());
        chapterTexts.put("...éjjeli védõtávolság alapján", rendezEjjelVedo());
        chapterTexts.put("...nappali hatásterület alapján", rendezNappalHatas());
        chapterTexts.put("...éjjeli hatásterület alapján", rendezEjjelHatas());
        chapterTexts.put("Nappali LAeq különbségek rendezése", rendezKulNappalLAeq());
        chapterTexts.put("Éjjeli LAeq különbségek rendezése", rendezKulEjjelLAeq());
        chapterTexts.put("Eredeti sorrend visszaállítása", rendezEredeti());
        chapterTexts.put("Állapotok különbsége", allapotokKulonbsege());

        return chapterTexts;
    }
    
    /**
     * Retrieves the text for a specific chapter based on the provided subsubsection.
     * 
     * @param subsubsection The subsubsection representing the chapter to retrieve the text for.
     * @return The text of the specified chapter, or "Chapter not found." if the chapter is not found.
     */
    public static String getTextForChapter(String subsubsection) {
        // Retrieve the chapter texts
        Map<String, String> chapterTexts = createChapterTexts();

        // Check if the chapter exists
        if (!chapterTexts.containsKey(subsubsection)) {
            return "Chapter not found.";
        }

        // Retrieve the chapter's text
        String chapterText = chapterTexts.get(subsubsection);

        return chapterText;
    }

    /**
    * Returns the general information text of the program.
    *
    * @return The general information text.
    */
    private static String altalanosInfo() {
        return ";;"
                + "                                                            "
                + "Közúti zajterhelés számító program;; "
                + "                                                                                    "
                + "2023;"
                + ";;"
                + "A számítások és figyelembe veendõ elõrírások alapját a;"
                + "       - 314/2005. (XII. 25.) Korm. rendelet,;"
                + "       - 284/2007. (X. 29.) Korm. rendelet,;"
                + "       - 27/2008. (XII. 3.) KvVM-EüM együttes rendelet,;"
                + "       - 93/2007. (XII. 18.) KvVM rendelet,;"
                + "       - MSZ 18150-1: 1998 szabvány,;"
                + "       - MSZ 15036: 2002 szabvány,;"
                + "       - MSZ ISO 1996-1: 2020,;"
                + "       - MSZ ISO 1996-2: 2021 szabvány,;"
                + "adták."
                + ";;;;;;;;;;;;;;;"
                + "v1.0;"
                + ";"
                + "Szabó Ákos;;";
    }
    
    /**
    * Returns the general description of usage.
    *
    * @return The general description of usage.
    */
    private static String altalanosHasznalat() {
        return ";;"
                + "                                                             "
                + "Általános leírás"
                + ";;;;"
                + "A program inputként shapefile-okat vár és outputként is shapefile-okat készít."
                + ";;"
                + "Az elvárt adatok az input shapefile-nál:;"
                + "       - Azonositó oszlop mindkét irányra;"
                + "       - I., II., III. akusztikai jármûkategóriájú forgalmi adat mindkét irányra;"
                + "       - Sebesség az I. akusztikai jármûkategóriára mindkét irányra"
                + ";;"
                + "Megnyitásnál az input oszlopneveket elõre meghatározott oszlopnevekkel kell "
                + "párosítani.; Fájl elsõ megnyitásánál a számítási paraméterek választhatóak"
                + ", melyek az egész fájlra vonatkoznak."
                + ";;"
                + "Megnyitott fájlnál a számítási paraméterek soronként változtathatóak "
                + "jobb klikkel."
                + ";;"
                + "Számítási mûveletek:;"
                + "       - Minden érték;"
                + "       - Egyenértékû A-hangnyomásszint;"
                + "       - Hangnyomásszint;"
                + "       - Védõtávolság;"
                + "       - Hatásterület;"
                + "       - Adott távolságon adódó zajterhelés;"
                + "       - Két fájl számított értékeinek különbsége"
                + ";;"
                + "Egyéb mûveletek:;"
                + "       - Rendezés számított értékek szerint;"
                + "       - Sortörlés;"
                + "       - Oszloptörlés"
                + ";;"
                + "Mentésnél két opció lehetséges:;"
                + "       1) Számított értékek mentése az eredeti geometriával együtt*;"
                + "       2) Buffer (védõtávolság, hatásterület) mentése input adatok (dbf file) és tengely nélkül;"
                + "*: a tengelyek töréspontjai 2 méterenkénti sûrítéssel lesznek ellátva"
                + ";;"
                + "A kimeneti adatok korai tervfázisokban közvetlenül felhasználatóak, vagy;"
                + "importálhatóak modellezõ programban további felhasználásra."
                + ";;"; 
    }
    
    /**
     * Returns the description of file opening.
     *
     * @return The description of file opening.
     */
    private static String megnyitas() {
        return ";;"
                + "                                                             "
                + "Fájlok megnyitása"
                + ";;;;"
                + "A program kizárólag .shp kiterjesztés tud megnyitni. Minden egyéb "
                + "formátumra hibaüzenetet dob."
                + ";;"
                + "Fájl megnyitása során az input oszlopneveket elõre meghatározott "
                + "oszlopnevekkel kell párosítani.;"
                + "(minden választható oszlopnevet fel kell használni!);"
                + "Második fájl megnyitásától kezdve használható a \"Használjuk az elõzõ párosítást?\";"
                + "lehetõség, mely automatikusan kitölti az oszlopneveket. Ennek feltétele, hogy minden,;"
                + "egymás után megnyitott fájl azonos mappában legyen.;"
                + ";;"
                + "Sikeres OK gomb után a program készít egy konfigurációs fájlt:;"
                + "       - fájl neve: fájlnév_config.txt;"
                + "       - fájl helye: ugyanaz a mappa, ahol a megnyitott .shp található;"
                + "       - funkciója: ugyanezen fájl bármikori megnyitásakor az oszlopnevek automatikusan;"
                + " kitöltésre kerülnek;"
                + "       - feltétele: a .shp-nek és a konfigurációs fájlnak ugyanabban a mappában kell lennie;"
                + ", ugyanazzal a névvel (+_config.txt);"
                + "       - megnyitás elõtt is elkészíthetõ a konfigurációs fájl, így kihagyható az oszlopnév párosítás.;;;"
                + "Példa egy konfigurációs fájl készítésére (opcioinális):;"
                + "       - beolvasandó shapefile neve: probaEgy.shp;"
                + "       - a shapefile-ban lévõ oszlopnevek: NO, I_AK_NAP~8, R_I_AK_~25, R_I_AK_~22, R_Azono~13,;"
                + "sebesseg, I_AK_EJJEL, R_sebesseg, III_AK_~10, II_AK_NA~9, R_III_A~27, III_AK_~12, R_III_A~24;"
                + "R_II_AK~26, II_AK_E~11, R_II_AK~23;"
                + ";"
                + "       - létre kell hozni egy üres .txt fájlt \"probaEgy_config.txt\" néven a .shp mappájában.;"
                + "       - a text fájlban elvégezzük a párosítást a fenti példa mentén;"
                + "(formátuma: input oszlopnév=elõre megadott oszlopnév);"
                + "NO=Azonosito;"
                + "I_AK_NAP~8=1akNappal;"
                + "R_I_AK_~25=R1akEjjel;"
                + "R_I_AK_~22=R1akNappal;"
                + "R_Azono~13=RAzonosito;"
                + "sebesseg=1akSebesseg;"
                + "I_AK_EJJEL=1akEjjel;"
                + "R_sebesseg=R1akSebesseg;"
                + "III_AK_~10=3akNappal;"
                + "II_AK_NA~9=2akNappal;"
                + "R_III_A~27=R3akEjjel;"
                + "III_AK_~12=3akEjjel;"
                + "R_III_A~24=R3akNappal;"
                + "R_II_AK~26=R2akEjjel;"
                + "II_AK_E~11=2akEjjel;"
                + "R_II_AK~23=R2akNappal;"
                + "       - már az elsõ megnyitásnál is automatikusan kitöltésre kerülnek az oszlopnevek.;"
                + ";;"
                + "A következõ felugró dialógus ablak a \"Számítási paraméterek módosítása\", melyen ;"
                + "a paraméterek meghatározása után az egész fájlra vonatkozóan érvényesek lesznek a ;"
                + "kiválasztott paraméterek.;"
                + "Alapbeállítása:;"
                + "       - Határérték: 65/55 dB;"
                + "       - C tényezõ: 12,5;"
                + "       - Kr: 0,5;"
                + "       - ß: 180°;"
                + "       - [K]: 0,29;"
                + "       - lejtés-emelkedés / forgalom típusa: 0 / egyenletes (azaz \"p\" paraméter = 0);"
                + "Megnyitás után a táblázat egyes sorainak paramétereit jobb klikkel lehet módosítani."
                + ";;";
    }   
 
    /**
     * Returns the description of file saving.
     *
     * @return The description of file saving.
     */
    private static String mentes() {
        return ";;"
                + "                                                             "
                + "Fájlok mentése"
                + ";;;;"
                + "A \"Mentés\" gomb mindig \"Mentés másként\" funkció szerint mûködik, elkerülve az;"
                + "esetleges eredeti (adatszolgáltatásként kapott) fájlok felülírását."
                + ";;"
                + "Az exportált .shp file tartalma:;"
                + "       - minden, a táblázatban található eredeti és számított sor és oszlop (dbf file),;"
                + "       - az eredeti geometria (úttengely).;;"
                + "A geometria töréspontjainak száma a mentés során 2 méterenkénti sûrítése történik,;"
                + "így az exportált fájl közvetlenül felhasználható modellezõ programban is;"
                + "(a geometria ráfeszül a terepre manuális hozzájárulás nélkül)."
                + ";;";
    }
    
    /**
    * Returns the description of closing the current window.
    *
    * @return The description of closing the current window.
    */
    private static String aktualisBezar() {
        return ";;"
                + "                                                             "
                + "Atkutális ablak bezárása"
                + ";;;;"
                + "Bezárja az aktív (képernyõn lévõ) táblázatot (fájlt).;"
                + ";"
                + "NEM kérdez rá a mentetlen fájlokra!!"
                + ";;";
    }
    
    /**
    * Returns the description of closing all windows.
    *
    * @return The description of closing all windows.
    */
    private static String mindentBezar() {
        return ";;"
                + "                                                             "
                + "Minden ablak bezárása"
                + ";;;;"
                + "Bezárja az összes táblázatot (fájlt).;"
                + ";"
                + "NEM kérdez rá a mentetlen fájlokra!!"
                + ";;";        
    }
    
    /**
    * Returns the description of exiting the program.
    *
    * @return The description of exiting the program.
    */
    private static String kilep() {
        return ";;"
                + "                                                             "
                + "Kilépés"
                + ";;;;"
                + "Kilép.;"
                + ";"
                + "NEM kérdez rá a mentetlen fájlokra!!"
                + ";;";          
    }
    
    /**
    * Returns the description of performing calculations for all values.
    *
    * @return The description of performing calculations for all values.
    */
    private static String szamitMind() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: minden érték számítása"
                + ";;;;"
                + "Ugyanebben a menüben lévõ, összes értékre elvégzi a számításokat és;"
                + "hozzáadja azokat a táblázathoz:;"
                + "    - LAeq nappal / éjjel;"
                + "    - Lw nappal / éjjel;"
                + "    - Védõtávolság nappal / éjjel;"
                + "    - Hatásterület nappal / éjjel"
                + ";;";          
    }
    
    /**
    * Returns the description of calculating equivalent A-weighted sound pressure level (LAeq).
    *
    * @return The description of calculating LAeq.
    */
    private static String szamitLAeq() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: egyenértékû A-hangnyomásszint számítása"
                + ";;;;"
                + "Kiszámítja és hozzáadja a táblázathoz az LAeq [dB] értékeket;"
                + "a nappali és éjjeli megítélési idõre."
                + ";;";            
    }
    
    /**
    * Returns the description of calculating sound power level (Lw).
    *
    * @return The description of calculating Lw.
    */
    private static String szamitLw() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: hangteljesítményszint számítása"
                + ";;;;"
                + "Kiszámítja és hozzáadja a táblázathoz az Lw [dB] értékeket;"
                + "a nappali és éjjeli megítélési idõre."
                + ";;";            
    }
    
    /**
    * Returns the description of calculating protective distance.
    *
    * @return The description of calculating protective distance.
    */
    private static String szamitVedo() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: védõtávolság számítása"
                + ";;;;"
                + "Kiszámítja és hozzáadja a táblázathoz a védõtávolság [m] értékeket;"
                + "a nappali és éjjeli megítélési idõre."
                + ";;";            
    }
    
    /**
    * Returns the description of calculating impact area distance.
    *
    * @return The description of calculating impact area distance.
    */
    private static String szamitHatas() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: hatásterület számítása"
                + ";;;;"
                + "Kiszámítja és hozzáadja a táblázathoz a hatásterület [m] értékeket;"
                + "a nappali és éjjeli megítélési idõre."
                + ";;";            
    }
    
    /**
    * Returns the description of calculating the noise level at a given distance.
    *
    * @return The description of calculating the noise level at a given distance.
    */
    private static String szamitAdottTavolsag() {
        return ";;"
                + "                                          "
                + "Számítási mûveletek: zajterhelés adott távolságon való számítása"
                + ";;;;"
                + "Kiszámítja és hozzáadja a táblázathoz a LAM,kö [dB] értékeket;"
                + "a nappali és éjjeli megítélési idõre.;"
                + ";"
                + "A felugró ablakban a távolság külön-külön meghatározható nappalra és éjjelre."
                + ";;";            
    }
    
    /**
    * Returns the description of deleting columns.
    *
    * @return The description of deleting columns.
    */
    private static String oszlopTorol() {
        return ";;"
                + "                                          "
                + "Oszlopok törlése"
                + ";;;;"
                + "A felugró ablakban bármelyik oszlop törölhetõ.;"
                + ";"
                + "!!!!!;"
                + "Ha input adatot tartalmazó oszlopot törlünk, a számítások már nem végezhetõk el.;"
                + "!!!!!"
                + ";;";            
    }

    /**
    * Returns the description of saving protective distances.
    *
    * @return The description of saving protective distances.
    */
    private static String vedoMentes() {
        return ";;"
                + "                                          "
                + "Védõtávolság mentése"
                + ";;;;"
                + "Amennyiben készült számítás védõtávolságra, akkor az lementhetõ .shp formátumban.;"
                + ";"
                + "Az exportált .shp fájl tartalma:;"
                + "       - kettõ darab buffer zóna:;"
                + "           - egy nappalra;"
                + "           - egy éjjelre;"
                + "       - a dbf file tartalma: egy-egy oszlop a nappali-éjjeli megnevezéssel.;"
                + ";"
                + "a .shp file nem fogja tartalmazni sem az eredeti geometriát (tengelyt),;"
                + "sem az input vagy számított adatokat."
                + ";;";     
    }
    
    /**
    * Returns the description of saving impact area distances.
    *
    * @return The description of saving impact area distances.
    */
    private static String hatasMentes() {
        return ";;"
                + "                                          "
                + "Hatásterület mentése"
                + ";;;;"
                + "Amennyiben készült számítás hatásterületre, akkor az lementhetõ .shp formátumban.;"
                + ";"
                + "Az exportált .shp fájl tartalma:;"
                + "       - kettõ darab buffer zóna:;"
                + "           - egy nappalra;"
                + "           - egy éjjelre;"
                + "       - a dbf file tartalma: egy-egy oszlop a nappali-éjjeli megnevezéssel.;"
                + ";"
                + "a .shp file nem fogja tartalmazni sem az eredeti geometriát (tengelyt),;"
                + "sem az input vagy számított adatokat."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on daytime LAeq.
    *
    * @return The description of sorting based on daytime LAeq.
    */
    private static String rendezNappalLaeq() {
        return ";;"
                + "                                                             "
                + "Rendezés: nappali LAeq alapján"
                + ";;;;"
                + "A táblázat rendezése nappali LAeq értékek alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on nighttime LAeq.
    *
    * @return The description of sorting based on nighttime LAeq.
    */
    private static String rendezEjjelLaeq() {
        return ";;"
                + "                                                             "
                + "Rendezés: éjjeli LAeq alapján"
                + ";;;;"
                + "A táblázat rendezése éjjeli LAeq értékek alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on daytime protective distance.
    *
    * @return The description of sorting based on daytime protective distance.
    */
    private static String rendezNappalVedo() {
        return ";;"
                + "                                                             "
                + "Rendezés: nappali védõtávolság alapján"
                + ";;;;"
                + "A táblázat rendezése nappali védõtávolság értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on nighttime protective distance.
    *
    * @return The description of sorting based on nighttime protective distance.
    */
    private static String rendezEjjelVedo() {
        return ";;"
                + "                                                             "
                + "Rendezés: éjjeli védõtávolság alapján"
                + ";;;;"
                + "A táblázat rendezése éjjeli védõtávolság értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on daytime impact area distance.
    *
    * @return The description of sorting based on daytime impact area distance.
    */
    private static String rendezNappalHatas() {
        return ";;"
                + "                                                             "
                + "Rendezés: nappali hatásterület alapján"
                + ";;;;"
                + "A táblázat rendezése nappali hatásterület értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on nighttime impact area distance.
    *
    * @return The description of sorting based on nighttime impact area distance.
    */
    private static String rendezEjjelHatas() {
        return ";;"
                + "                                                             "
                + "Rendezés: éjjel hatásterület alapján"
                + ";;;;"
                + "A táblázat rendezése éjjeli hatásterület értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on the difference of daytime LAeq values between two files
    *
    * @return The description of sorting based on the difference of daytime LAeq values between two files.
    */
    private static String rendezKulNappalLAeq() {
        return ";;"
                + "                                                             "
                + "Rendezés: két fájl különbségének nappali LAeq-ja alapján"
                + ";;;;"
                + "A táblázat rendezése két fájl különbségének nappali LAeq értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of sorting based on the difference of nighttime LAeq values between two files
    *
    * @return The description of sorting based on the difference of nighttime LAeq values between two files.
    */
    private static String rendezKulEjjelLAeq() {
        return ";;"
                + "                                                             "
                + "Rendezés: két fájl különbségének éjjeli LAeq-ja alapján"
                + ";;;;"
                + "A táblázat rendezése két fájl különbségének éjjeli LAeq értékei alapján, csökkenõ sorrendben."
                + ";;";     
    }
    
    /**
    * Returns the description of restoring the original order of the table
    *
    * @return The description of restoring the original order of the table.
    */
    private static String rendezEredeti() {
        return ";;"
                + "                                                             "
                + "Rendezés: eredeti sorrend"
                + ";;;;"
                + "A táblázat eredeti sorrendjének visszaállítása."
                + ";;";     
    }
    
    /**
    * Returns the description of calculating the difference between two files' computed values
    *
    * @return The description of calculating the difference between two files' computed values.
    */
    private static String allapotokKulonbsege() {
        return ";;"
                + "                                                             "
                + "Különbségszámítás"
                + ";;;;"
                + "Két fájl számított értékeinek különbségét kalkulálja és adja hozzá a táblázathoz.;"
                + ";"
                + "A felugró ablakban meg kell adni a két (megnyitott) fájlt és a számítani kívánt értéket.;"
                + ";"
                + "Ha a 2. megadott fájlban nem található egy sor az 1. megadott fájlban (tehát ÚJ utat jelöl a sor);"
                + "akkor annak a különbségnek az értéke: 100 (= új zajforrás)"
                + ";"
                + "Ha az 1. megadott fájlban nem található egy sor a 2. megadott fájlban (tehát LEZÁRT utat jelöl a sor);"
                + "akkor annak a különbségnek az értéke: -100 (= megszûnõ zajforrás)"
                + ";;";     
    }
    
}
