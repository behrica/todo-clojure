(defproject todo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-time "0.6.0"]
                 [korma "0.3.0-RC6"]
                 [com.h2database/h2 "1.3.170"]
                 [clj-liquibase "0.4.0"]
                 [clj-dbcp "0.8.0"]
                 [clj-jdbcutil "0.1.0"]
                 [ring "1.2.1"]
                 [ring-json-params "0.1.3"]
                 [compojure "1.1.6"]
                 [clj-json "0.3.2"]
                 ]
  :main ^:skip-aot todo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {
                    :dependencies [[midje "1.5.1"][clj-ns-browser "1.3.1"]]
                    :plugins [[lein-midje "3.1.1"][lein-ancient "0.5.2"]
                              ]}
             }
)
