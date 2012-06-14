package sc2012.teamdojo.pagerank;


import java.util.List;
import java.util.Set;

public class PageRankMatrixBuilder  {
    
//    private static final Logger logger = Logger.getLogger(PageRankMatrixBuilder.class);

    private PageRankMatrixH matrixH;
    public PageRankMatrixBuilder(CrawlData crawlData) {
        this.crawlData = crawlData;
    }
    
    public PageRankMatrixH getH() {
        return matrixH;
    }

    public void run() {
        this.matrixH = buildMatrixH(crawlData.getKnownUrlsDB(), crawlData.getPageLinkDB());
    }
    
    private PageRankMatrixH buildMatrixH(KnownUrlDB knownUrlDB, PageLinkDB pageLinkDB) {

//    	logger.info("starting calculation of matrix H...");
        
    	// only consider URLs that with fetched and parsed content
        List<String> allProcessedUrls = knownUrlDB.findProcessedUrls(KnownUrlEntry.STATUS_PROCESSED_SUCCESS);
        
        PageRankMatrixH pageMatrix = new PageRankMatrixH( allProcessedUrls.size() );
        
        for(String url : allProcessedUrls) {
        
            // register url here in case it has no outlinks.
            pageMatrix.addLink(url);
            
        	Set<String> pageOutlinks = pageLinkDB.getOutlinks(url);
            
        	for(String outlink : pageOutlinks) {
            
        		// only consider URLs with parsed content
                if( knownUrlDB.isSuccessfullyProcessed(outlink) ) {
                    pageMatrix.addLink(url, outlink);
                }
            }
        }
        
        pageMatrix.calculate();
        
//        logger.info("matrix H is ready. Matrix size: " + pageMatrix.getMatrix().length);
        
        return pageMatrix;
    }
}
