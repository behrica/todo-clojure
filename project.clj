(defproject todo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-time "0.6.0"]
                 [korma "0.3.0-RC6"]
                 [com.h2database/h2 "1.3.174"]
                 [clj-liquibase "0.4.0"]
                 [clj-dbcp "0.8.1"]
                 [clj-jdbcutil "0.1.0"]
                 [ring "1.2.1"]
                 [ring-json-params "0.1.3"]
                 [compojure "1.1.6"]
                 [clj-json "0.5.3"]
                 [org.seleniumhq.selenium/selenium-server "2.29.1"]
                 [midje "1.6-beta1"]
                 ;[org.apache.httpcomponents/httpcore "4.2.3"]
                  [clj-http "0.7.7"]
                  [com.ashafa/clutch "0.4.0-RC1"]

		 [lein-light-nrepl "0.0.9"] [org.clojure/tools.reader "0.7.10"]
                 ]
  :main ^:skip-aot todo.core
  :plugins [[lein-midje "3.1.1"]
            [lein-ancient "0.5.2"]
            [lein-shell "0.3.0"]
            [lein-ring "0.8.8"]
            [lein-karma "0.1.0"]
            [lein-protractor "0.1.1-SNAPSHOT"]
            ]
  :profiles {:uberjar {:aot :all}}
  :ring {:handler todo.web/app}
  :protractor {:init todo.korma.db/init-db
               :chromedriver "/home/carsten/bin/chromedriver"
               :protractorconfig "resources/protractor_conf.js"
               }
  :aliases {"build" ["do" "clean," "install," "midje," "karma," "protractor"]}
  :jvm-opts ["-Duser.timezone=UTC"]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
)

