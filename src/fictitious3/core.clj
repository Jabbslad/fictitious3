(ns fictitious3.core
  (:require [fictitious3.filestore :as fs])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (fs/objects "."))
