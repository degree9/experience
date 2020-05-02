(defn read-file   [file] (read-string (slurp file)))
(defn shadow-cljs-edn [] (read-file "./shadow-cljs.edn"))
(defn get-deps    []     (:dependencies (shadow-cljs-edn)))

(set-env!
  :dependencies (get-deps)
  :resource-paths #{"src"})

(require
 '[degree9.boot-semver :refer :all])

(task-options!
 pom    {:project 'degree9/experience
         :description "Degree9 Experience for Enterprise."
         :url "http://github.com/degree9/experience"
         :scm {:url "http://github.com/degree9/experience"}})

(deftask deploy
  "Build project for deployment to clojars."
  []
  (comp
    (version)
    (build-jar)
    (push-release)))

(deftask develop
  "Build project for local development."
  []
  (comp
    (version :develop true
             :pre-release 'snapshot)
    (watch)
    (build-jar)))
