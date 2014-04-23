(ns fictitious3.filestore
  (:import (java.security MessageDigest)))

(defn starts-with [prefix name]
  "return objects with a keys that match the given prefix"
  (.startsWith (:file-name name) prefix))

(defn marker [start coll]
  "return objects that follow a given key name "
  (drop-while #(not (starts-with start %)) coll))

(defn max-keys [n coll]
  "return n objects"
  (take n coll))

(defn prefix [x coll]
  "return objects with a given key prefix"
  (filter (partial starts-with x) coll))

;; Move info utils
(defn md5 [key]
  "calculate the md5 checksum of a file"
  (let [md5 (MessageDigest/getInstance "MD5")
        bytes  (.digest md5 (.getBytes (slurp key)))
        hex (.toString (BigInteger. bytes) 16)]
    (if (< (count hex) 32)
      (str "0" hex)
      hex)))

(defn object [file]
  "Take a java.io.File object and returns a hash of properties"
  {:file-name (.getName file)
   :path (.getPath file)
   :md5 (md5 file)})

(defn objects [bucket]
  "return objects from a bucket"
  (map object (filter #(.isFile %) (file-seq (clojure.java.io/file bucket)))))
