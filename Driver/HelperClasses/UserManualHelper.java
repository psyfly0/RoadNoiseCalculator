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
                new DefaultMutableTreeNode("Haszn�lati �tmutat�");
        
        // Create the chapters
        DefaultMutableTreeNode chapter0Node = 
                new DefaultMutableTreeNode("�ltal�nos le�r�s");
        DefaultMutableTreeNode chapter1Node = 
                new DefaultMutableTreeNode("F�jl men�");
        DefaultMutableTreeNode chapter2Node = 
                new DefaultMutableTreeNode("M�veletek men�");
        DefaultMutableTreeNode chapter3Node = 
                new DefaultMutableTreeNode("Rendez�s / K�l�nbs�g men�");

        // Create the subsections for Chapter 0
        DefaultMutableTreeNode subsection0_1_Node = 
                new DefaultMutableTreeNode("�ltal�nos inform�ci�");
        DefaultMutableTreeNode subsection0_2_Node = 
                new DefaultMutableTreeNode("Haszn�lat �ltal�nos le�r�sa");

        chapter0Node.add(subsection0_1_Node);
        chapter0Node.add(subsection0_2_Node);

        // Create the subsections for Chapter 1
        DefaultMutableTreeNode subsection1_1_Node = 
                new DefaultMutableTreeNode("Megnyit�s");
        DefaultMutableTreeNode subsection1_2_Node = 
                new DefaultMutableTreeNode("Ment�s");
        DefaultMutableTreeNode subsection1_3_Node = 
                new DefaultMutableTreeNode("Aktu�lis projekt bez�r�sa");
        DefaultMutableTreeNode subsection1_4_Node = 
                new DefaultMutableTreeNode("Minden projekt bez�r�sa");
        DefaultMutableTreeNode subsection1_5_Node = 
                new DefaultMutableTreeNode("Kil�p�s");
        chapter1Node.add(subsection1_1_Node);
        chapter1Node.add(subsection1_2_Node);
        chapter1Node.add(subsection1_3_Node);
        chapter1Node.add(subsection1_4_Node);
        chapter1Node.add(subsection1_5_Node);

        // Create the subsections for Chapter 2
        DefaultMutableTreeNode subsection2_1_Node = 
                new DefaultMutableTreeNode("Sz�m�t�si M�veletek");
        DefaultMutableTreeNode subsection2_1_1_Node = 
                new DefaultMutableTreeNode("Sz�m�t�s minden �rt�kre");
        DefaultMutableTreeNode subsection2_1_2_Node = 
                new DefaultMutableTreeNode("Egyen�rt�k� A-hangnyom�sszint sz�m�t�sa");
        DefaultMutableTreeNode subsection2_1_3_Node = 
                new DefaultMutableTreeNode("Hangteljes�tm�nyszint sz�m�t�sa");
        DefaultMutableTreeNode subsection2_1_4_Node = 
                new DefaultMutableTreeNode("V�d�t�vols�g sz�m�t�sa");
        DefaultMutableTreeNode subsection2_1_5_Node = 
                new DefaultMutableTreeNode("Hat�ster�let sz�m�t�sa");
        DefaultMutableTreeNode subsection2_1_6_Node = 
                new DefaultMutableTreeNode("Adott t�vols�gon ad�d� zajterhel�s");

        DefaultMutableTreeNode subsection2_2_Node = 
                new DefaultMutableTreeNode("Oszlopok t�rl�se");
        DefaultMutableTreeNode subsection2_3_Node = 
                new DefaultMutableTreeNode("V�d�t�vols�g ment�se");
        DefaultMutableTreeNode subsection2_4_Node = 
                new DefaultMutableTreeNode("Hat�ster�let ment�se");

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
                new DefaultMutableTreeNode("Rendez�s");
        DefaultMutableTreeNode subsection3_1_1_Node = 
                new DefaultMutableTreeNode("...nappali LAeq alapj�n");
        DefaultMutableTreeNode subsection3_1_2_Node = 
                new DefaultMutableTreeNode("...�jjeli LAeq alapj�n");
        DefaultMutableTreeNode subsection3_1_3_Node = 
                new DefaultMutableTreeNode("...nappali v�d�t�vols�g alapj�n");
        DefaultMutableTreeNode subsection3_1_4_Node = 
                new DefaultMutableTreeNode("...�jjeli v�d�t�vols�g alapj�n");
        DefaultMutableTreeNode subsection3_1_5_Node = 
                new DefaultMutableTreeNode("...nappali hat�ster�let alapj�n");
        DefaultMutableTreeNode subsection3_1_6_Node = 
                new DefaultMutableTreeNode("...�jjeli hat�ster�let alapj�n");
        DefaultMutableTreeNode subsection3_1_7_Node = 
                new DefaultMutableTreeNode("Nappali LAeq k�l�nbs�gek rendez�se");
        DefaultMutableTreeNode subsection3_1_8_Node = 
                new DefaultMutableTreeNode("�jjeli LAeq k�l�nbs�gek rendez�se");
        DefaultMutableTreeNode subsection3_1_9_Node = 
                new DefaultMutableTreeNode("Eredeti sorrend vissza�ll�t�sa");

        DefaultMutableTreeNode subsection3_2_Node = 
                new DefaultMutableTreeNode("�llapotok k�l�nbs�ge");

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
        chapterTexts.put("�ltal�nos inform�ci�", altalanosInfo());
        chapterTexts.put("Haszn�lat �ltal�nos le�r�sa", altalanosHasznalat());

        // Chapter 1 texts
        chapterTexts.put("Megnyit�s", megnyitas());
        chapterTexts.put("Ment�s", mentes());
        chapterTexts.put("Aktu�lis projekt bez�r�sa", aktualisBezar());
        chapterTexts.put("Minden projekt bez�r�sa", mindentBezar());
        chapterTexts.put("Kil�p�s", kilep());

        // Chapter 2 texts
        chapterTexts.put("Sz�m�t�s minden �rt�kre", szamitMind());
        chapterTexts.put("Egyen�rt�k� A-hangnyom�sszint sz�m�t�sa", szamitLAeq());
        chapterTexts.put("Hangteljes�tm�nyszint sz�m�t�sa", szamitLw());
        chapterTexts.put("V�d�t�vols�g sz�m�t�sa", szamitVedo());
        chapterTexts.put("Hat�ster�let sz�m�t�sa", szamitHatas());
        chapterTexts.put("Adott t�vols�gon ad�d� zajterhel�s", szamitAdottTavolsag());
        chapterTexts.put("Oszlopok t�rl�se", oszlopTorol());
        chapterTexts.put("V�d�t�vols�g ment�se", vedoMentes());
        chapterTexts.put("Hat�ster�let ment�se", hatasMentes());

        // Chapter 3 texts
        chapterTexts.put("...nappali LAeq alapj�n", rendezNappalLaeq());
        chapterTexts.put("...�jjeli LAeq alapj�n", rendezEjjelLaeq());
        chapterTexts.put("...nappali v�d�t�vols�g alapj�n", rendezNappalVedo());
        chapterTexts.put("...�jjeli v�d�t�vols�g alapj�n", rendezEjjelVedo());
        chapterTexts.put("...nappali hat�ster�let alapj�n", rendezNappalHatas());
        chapterTexts.put("...�jjeli hat�ster�let alapj�n", rendezEjjelHatas());
        chapterTexts.put("Nappali LAeq k�l�nbs�gek rendez�se", rendezKulNappalLAeq());
        chapterTexts.put("�jjeli LAeq k�l�nbs�gek rendez�se", rendezKulEjjelLAeq());
        chapterTexts.put("Eredeti sorrend vissza�ll�t�sa", rendezEredeti());
        chapterTexts.put("�llapotok k�l�nbs�ge", allapotokKulonbsege());

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
                + "K�z�ti zajterhel�s sz�m�t� program;; "
                + "                                                                                    "
                + "2023;"
                + ";;"
                + "A sz�m�t�sok �s figyelembe veend� el�r�r�sok alapj�t a;"
                + "       - 314/2005. (XII. 25.) Korm. rendelet,;"
                + "       - 284/2007. (X. 29.) Korm. rendelet,;"
                + "       - 27/2008. (XII. 3.) KvVM-E�M egy�ttes rendelet,;"
                + "       - 93/2007. (XII. 18.) KvVM rendelet,;"
                + "       - MSZ 18150-1: 1998 szabv�ny,;"
                + "       - MSZ 15036: 2002 szabv�ny,;"
                + "       - MSZ ISO 1996-1: 2020,;"
                + "       - MSZ ISO 1996-2: 2021 szabv�ny,;"
                + "adt�k."
                + ";;;;;;;;;;;;;;;"
                + "v1.0;"
                + ";"
                + "Szab� �kos;;";
    }
    
    /**
    * Returns the general description of usage.
    *
    * @return The general description of usage.
    */
    private static String altalanosHasznalat() {
        return ";;"
                + "                                                             "
                + "�ltal�nos le�r�s"
                + ";;;;"
                + "A program inputk�nt shapefile-okat v�r �s outputk�nt is shapefile-okat k�sz�t."
                + ";;"
                + "Az elv�rt adatok az input shapefile-n�l:;"
                + "       - Azonosit� oszlop mindk�t ir�nyra;"
                + "       - I., II., III. akusztikai j�rm�kateg�ri�j� forgalmi adat mindk�t ir�nyra;"
                + "       - Sebess�g az I. akusztikai j�rm�kateg�ri�ra mindk�t ir�nyra"
                + ";;"
                + "Megnyit�sn�l az input oszlopneveket el�re meghat�rozott oszlopnevekkel kell "
                + "p�ros�tani.; F�jl els� megnyit�s�n�l a sz�m�t�si param�terek v�laszthat�ak"
                + ", melyek az eg�sz f�jlra vonatkoznak."
                + ";;"
                + "Megnyitott f�jln�l a sz�m�t�si param�terek soronk�nt v�ltoztathat�ak "
                + "jobb klikkel."
                + ";;"
                + "Sz�m�t�si m�veletek:;"
                + "       - Minden �rt�k;"
                + "       - Egyen�rt�k� A-hangnyom�sszint;"
                + "       - Hangnyom�sszint;"
                + "       - V�d�t�vols�g;"
                + "       - Hat�ster�let;"
                + "       - Adott t�vols�gon ad�d� zajterhel�s;"
                + "       - K�t f�jl sz�m�tott �rt�keinek k�l�nbs�ge"
                + ";;"
                + "Egy�b m�veletek:;"
                + "       - Rendez�s sz�m�tott �rt�kek szerint;"
                + "       - Sort�rl�s;"
                + "       - Oszlopt�rl�s"
                + ";;"
                + "Ment�sn�l k�t opci� lehets�ges:;"
                + "       1) Sz�m�tott �rt�kek ment�se az eredeti geometri�val egy�tt*;"
                + "       2) Buffer (v�d�t�vols�g, hat�ster�let) ment�se input adatok (dbf file) �s tengely n�lk�l;"
                + "*: a tengelyek t�r�spontjai 2 m�terenk�nti s�r�t�ssel lesznek ell�tva"
                + ";;"
                + "A kimeneti adatok korai tervf�zisokban k�zvetlen�l felhaszn�lat�ak, vagy;"
                + "import�lhat�ak modellez� programban tov�bbi felhaszn�l�sra."
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
                + "F�jlok megnyit�sa"
                + ";;;;"
                + "A program kiz�r�lag .shp kiterjeszt�s tud megnyitni. Minden egy�b "
                + "form�tumra hiba�zenetet dob."
                + ";;"
                + "F�jl megnyit�sa sor�n az input oszlopneveket el�re meghat�rozott "
                + "oszlopnevekkel kell p�ros�tani.;"
                + "(minden v�laszthat� oszlopnevet fel kell haszn�lni!);"
                + "M�sodik f�jl megnyit�s�t�l kezdve haszn�lhat� a \"Haszn�ljuk az el�z� p�ros�t�st?\";"
                + "lehet�s�g, mely automatikusan kit�lti az oszlopneveket. Ennek felt�tele, hogy minden,;"
                + "egym�s ut�n megnyitott f�jl azonos mapp�ban legyen.;"
                + ";;"
                + "Sikeres OK gomb ut�n a program k�sz�t egy konfigur�ci�s f�jlt:;"
                + "       - f�jl neve: f�jln�v_config.txt;"
                + "       - f�jl helye: ugyanaz a mappa, ahol a megnyitott .shp tal�lhat�;"
                + "       - funkci�ja: ugyanezen f�jl b�rmikori megnyit�sakor az oszlopnevek automatikusan;"
                + " kit�lt�sre ker�lnek;"
                + "       - felt�tele: a .shp-nek �s a konfigur�ci�s f�jlnak ugyanabban a mapp�ban kell lennie;"
                + ", ugyanazzal a n�vvel (+_config.txt);"
                + "       - megnyit�s el�tt is elk�sz�thet� a konfigur�ci�s f�jl, �gy kihagyhat� az oszlopn�v p�ros�t�s.;;;"
                + "P�lda egy konfigur�ci�s f�jl k�sz�t�s�re (opcioin�lis):;"
                + "       - beolvasand� shapefile neve: probaEgy.shp;"
                + "       - a shapefile-ban l�v� oszlopnevek: NO, I_AK_NAP~8, R_I_AK_~25, R_I_AK_~22, R_Azono~13,;"
                + "sebesseg, I_AK_EJJEL, R_sebesseg, III_AK_~10, II_AK_NA~9, R_III_A~27, III_AK_~12, R_III_A~24;"
                + "R_II_AK~26, II_AK_E~11, R_II_AK~23;"
                + ";"
                + "       - l�tre kell hozni egy �res .txt f�jlt \"probaEgy_config.txt\" n�ven a .shp mapp�j�ban.;"
                + "       - a text f�jlban elv�gezz�k a p�ros�t�st a fenti p�lda ment�n;"
                + "(form�tuma: input oszlopn�v=el�re megadott oszlopn�v);"
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
                + "       - m�r az els� megnyit�sn�l is automatikusan kit�lt�sre ker�lnek az oszlopnevek.;"
                + ";;"
                + "A k�vetkez� felugr� dial�gus ablak a \"Sz�m�t�si param�terek m�dos�t�sa\", melyen ;"
                + "a param�terek meghat�roz�sa ut�n az eg�sz f�jlra vonatkoz�an �rv�nyesek lesznek a ;"
                + "kiv�lasztott param�terek.;"
                + "Alapbe�ll�t�sa:;"
                + "       - Hat�r�rt�k: 65/55 dB;"
                + "       - C t�nyez�: 12,5;"
                + "       - Kr: 0,5;"
                + "       - �: 180�;"
                + "       - [K]: 0,29;"
                + "       - lejt�s-emelked�s / forgalom t�pusa: 0 / egyenletes (azaz \"p\" param�ter = 0);"
                + "Megnyit�s ut�n a t�bl�zat egyes sorainak param�tereit jobb klikkel lehet m�dos�tani."
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
                + "F�jlok ment�se"
                + ";;;;"
                + "A \"Ment�s\" gomb mindig \"Ment�s m�sk�nt\" funkci� szerint m�k�dik, elker�lve az;"
                + "esetleges eredeti (adatszolg�ltat�sk�nt kapott) f�jlok fel�l�r�s�t."
                + ";;"
                + "Az export�lt .shp file tartalma:;"
                + "       - minden, a t�bl�zatban tal�lhat� eredeti �s sz�m�tott sor �s oszlop (dbf file),;"
                + "       - az eredeti geometria (�ttengely).;;"
                + "A geometria t�r�spontjainak sz�ma a ment�s sor�n 2 m�terenk�nti s�r�t�se t�rt�nik,;"
                + "�gy az export�lt f�jl k�zvetlen�l felhaszn�lhat� modellez� programban is;"
                + "(a geometria r�fesz�l a terepre manu�lis hozz�j�rul�s n�lk�l)."
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
                + "Atkut�lis ablak bez�r�sa"
                + ";;;;"
                + "Bez�rja az akt�v (k�perny�n l�v�) t�bl�zatot (f�jlt).;"
                + ";"
                + "NEM k�rdez r� a mentetlen f�jlokra!!"
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
                + "Minden ablak bez�r�sa"
                + ";;;;"
                + "Bez�rja az �sszes t�bl�zatot (f�jlt).;"
                + ";"
                + "NEM k�rdez r� a mentetlen f�jlokra!!"
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
                + "Kil�p�s"
                + ";;;;"
                + "Kil�p.;"
                + ";"
                + "NEM k�rdez r� a mentetlen f�jlokra!!"
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
                + "Sz�m�t�si m�veletek: minden �rt�k sz�m�t�sa"
                + ";;;;"
                + "Ugyanebben a men�ben l�v�, �sszes �rt�kre elv�gzi a sz�m�t�sokat �s;"
                + "hozz�adja azokat a t�bl�zathoz:;"
                + "    - LAeq nappal / �jjel;"
                + "    - Lw nappal / �jjel;"
                + "    - V�d�t�vols�g nappal / �jjel;"
                + "    - Hat�ster�let nappal / �jjel"
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
                + "Sz�m�t�si m�veletek: egyen�rt�k� A-hangnyom�sszint sz�m�t�sa"
                + ";;;;"
                + "Kisz�m�tja �s hozz�adja a t�bl�zathoz az LAeq [dB] �rt�keket;"
                + "a nappali �s �jjeli meg�t�l�si id�re."
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
                + "Sz�m�t�si m�veletek: hangteljes�tm�nyszint sz�m�t�sa"
                + ";;;;"
                + "Kisz�m�tja �s hozz�adja a t�bl�zathoz az Lw [dB] �rt�keket;"
                + "a nappali �s �jjeli meg�t�l�si id�re."
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
                + "Sz�m�t�si m�veletek: v�d�t�vols�g sz�m�t�sa"
                + ";;;;"
                + "Kisz�m�tja �s hozz�adja a t�bl�zathoz a v�d�t�vols�g [m] �rt�keket;"
                + "a nappali �s �jjeli meg�t�l�si id�re."
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
                + "Sz�m�t�si m�veletek: hat�ster�let sz�m�t�sa"
                + ";;;;"
                + "Kisz�m�tja �s hozz�adja a t�bl�zathoz a hat�ster�let [m] �rt�keket;"
                + "a nappali �s �jjeli meg�t�l�si id�re."
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
                + "Sz�m�t�si m�veletek: zajterhel�s adott t�vols�gon val� sz�m�t�sa"
                + ";;;;"
                + "Kisz�m�tja �s hozz�adja a t�bl�zathoz a LAM,k� [dB] �rt�keket;"
                + "a nappali �s �jjeli meg�t�l�si id�re.;"
                + ";"
                + "A felugr� ablakban a t�vols�g k�l�n-k�l�n meghat�rozhat� nappalra �s �jjelre."
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
                + "Oszlopok t�rl�se"
                + ";;;;"
                + "A felugr� ablakban b�rmelyik oszlop t�r�lhet�.;"
                + ";"
                + "!!!!!;"
                + "Ha input adatot tartalmaz� oszlopot t�rl�nk, a sz�m�t�sok m�r nem v�gezhet�k el.;"
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
                + "V�d�t�vols�g ment�se"
                + ";;;;"
                + "Amennyiben k�sz�lt sz�m�t�s v�d�t�vols�gra, akkor az lementhet� .shp form�tumban.;"
                + ";"
                + "Az export�lt .shp f�jl tartalma:;"
                + "       - kett� darab buffer z�na:;"
                + "           - egy nappalra;"
                + "           - egy �jjelre;"
                + "       - a dbf file tartalma: egy-egy oszlop a nappali-�jjeli megnevez�ssel.;"
                + ";"
                + "a .shp file nem fogja tartalmazni sem az eredeti geometri�t (tengelyt),;"
                + "sem az input vagy sz�m�tott adatokat."
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
                + "Hat�ster�let ment�se"
                + ";;;;"
                + "Amennyiben k�sz�lt sz�m�t�s hat�ster�letre, akkor az lementhet� .shp form�tumban.;"
                + ";"
                + "Az export�lt .shp f�jl tartalma:;"
                + "       - kett� darab buffer z�na:;"
                + "           - egy nappalra;"
                + "           - egy �jjelre;"
                + "       - a dbf file tartalma: egy-egy oszlop a nappali-�jjeli megnevez�ssel.;"
                + ";"
                + "a .shp file nem fogja tartalmazni sem az eredeti geometri�t (tengelyt),;"
                + "sem az input vagy sz�m�tott adatokat."
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
                + "Rendez�s: nappali LAeq alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se nappali LAeq �rt�kek alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: �jjeli LAeq alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se �jjeli LAeq �rt�kek alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: nappali v�d�t�vols�g alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se nappali v�d�t�vols�g �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: �jjeli v�d�t�vols�g alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se �jjeli v�d�t�vols�g �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: nappali hat�ster�let alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se nappali hat�ster�let �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: �jjel hat�ster�let alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se �jjeli hat�ster�let �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: k�t f�jl k�l�nbs�g�nek nappali LAeq-ja alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se k�t f�jl k�l�nbs�g�nek nappali LAeq �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: k�t f�jl k�l�nbs�g�nek �jjeli LAeq-ja alapj�n"
                + ";;;;"
                + "A t�bl�zat rendez�se k�t f�jl k�l�nbs�g�nek �jjeli LAeq �rt�kei alapj�n, cs�kken� sorrendben."
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
                + "Rendez�s: eredeti sorrend"
                + ";;;;"
                + "A t�bl�zat eredeti sorrendj�nek vissza�ll�t�sa."
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
                + "K�l�nbs�gsz�m�t�s"
                + ";;;;"
                + "K�t f�jl sz�m�tott �rt�keinek k�l�nbs�g�t kalkul�lja �s adja hozz� a t�bl�zathoz.;"
                + ";"
                + "A felugr� ablakban meg kell adni a k�t (megnyitott) f�jlt �s a sz�m�tani k�v�nt �rt�ket.;"
                + ";"
                + "Ha a 2. megadott f�jlban nem tal�lhat� egy sor az 1. megadott f�jlban (teh�t �J utat jel�l a sor);"
                + "akkor annak a k�l�nbs�gnek az �rt�ke: 100 (= �j zajforr�s)"
                + ";"
                + "Ha az 1. megadott f�jlban nem tal�lhat� egy sor a 2. megadott f�jlban (teh�t LEZ�RT utat jel�l a sor);"
                + "akkor annak a k�l�nbs�gnek az �rt�ke: -100 (= megsz�n� zajforr�s)"
                + ";;";     
    }
    
}
