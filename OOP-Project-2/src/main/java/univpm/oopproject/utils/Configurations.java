package univpm.oopproject.utils;

/**
 * Classe astratta che contiene le stringhe di configurazione.
 * @author Samuele Perticarari & Martina Rossi
 *
 */
public abstract class Configurations
{
	/**
	 * URL del dataset da scaricare e parsare.
	 */
	public static final String FILE_URL = "https://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/ilc_li04.tsv.gz&unzip=true";
	
	/**
	 * Nome del file di destinazione del dataset.
	 */
	public static final String FILE_NAME = System.getProperty("java.io.tmpdir") + "dataset.tsv";
	

}
