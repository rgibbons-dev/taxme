(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'rgd/taxme)
(def version "0.1.0")
(def class-dir "target/classes")
(def uber-file (format "target/%s-%s.jar" (name lib) version))

(def basis (b/create-basis {:project "deps.edn"}))

(defn clean [_]
  (b/delete {:path "target"})
  (println "Build folder target/ removed"))

(defn uber [_]
  (clean nil)
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir 
           :uber-file uber-file
           :basis basis 
           :main 'taxme.core}))