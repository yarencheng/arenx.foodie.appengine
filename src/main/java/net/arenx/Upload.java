package net.arenx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Upload extends HttpServlet {
	private static final Logger log = Logger.getLogger(Upload.class.getName());
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("photo");
        
        if (0 == blobKeys.size()) {
        	throw new IllegalArgumentException("no photo");
        }
        
        if (1 < blobKeys.size()) {
        	log.warning("accept the 1st photo; ignore the others");
        }
        
        Long id=Long.parseLong(req.getParameter("id"));
        BlobKey blob = blobKeys.get(0);
        
        PhotoManager.instance().setBlob(id, blob);

        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendRedirect("/");
        } else {
            res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
        }
    }
}
