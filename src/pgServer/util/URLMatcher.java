package pgServer.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class URLMatcher {

        private String urlQuery;
        private String urlString;
        private String[] urlParams;
        private String[] urlQueryParams;
        private Map<String,Object> map;
        
        public URLMatcher ( String urlQuery, String urlString  ) {
            this.urlQuery = urlQuery;
            this.urlString = urlString;
            this.urlQueryParams = urlQuery.split("/");
            this.urlParams = urlString.split("/");
            if ( this.urlQueryParams.length == this.urlParams.length ) {
                Map<String, Object> root = new HashMap<String, Object>();
                for ( int i = 0; i<this.urlQueryParams.length; i++) {
                    if ( URLMatcher.isQuery(urlQueryParams[i])) {
                        root.put(urlQueryParams[i].substring(1, urlQueryParams[i].length()), urlParams[i]);
                    }
                }
                map = root;
            }

        }

        public String getValue ( String key) {
            try{
                return (String) map.get(key);
            } catch ( Exception e ) {
                return null;
            }
        }

        public static boolean isMatching (String urlQuery, String urlString) {

            String [] urlQueryParams = urlQuery.split("/");
            String [] urlParams = urlString.split("/");
            if ( urlQueryParams.length != urlParams.length ) {
                return false;
            }
            for ( int i=0 ; i<urlQueryParams.length; i++) {
                if ( URLMatcher.isQuery(urlQueryParams[i]) ) {
                    continue;
                }
                if ( !urlQueryParams[i].equals(urlParams[i]) ) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isQuery ( String param ) {
            return param.startsWith(":");
        }
}
