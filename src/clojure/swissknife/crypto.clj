(ns swissknife.crypto
  (:import [javax.crypto Cipher KeyGenerator SecretKey]
           [javax.crypto.spec SecretKeySpec]
           [java.security SecureRandom]
           [org.apache.commons.codec.binary Base64]))
;;; Credit goes to user2059829 http://stackoverflow.com/questions/10221257/is-there-an-aes-library-for-clojure


(defn base64 [b]
  (Base64/encodeBase64String b))


(defn debase64 [s]
  (Base64/decodeBase64 (byte-array (map byte s))))


(defn get-raw-key [seed]
  (let [keygen (KeyGenerator/getInstance "AES")
        sr (SecureRandom/getInstance "SHA1PRNG")]
    (.setSeed sr (byte-array (map byte seed)))
    (.init keygen 128 sr)
    (.. keygen generateKey getEncoded)))


(defn get-cipher [mode seed]
  (let [key-spec (SecretKeySpec. (get-raw-key seed) "AES")
        cipher (Cipher/getInstance "AES")]
    (.init cipher mode key-spec)
    cipher))


(defn encrypt [text key]
  (let [bytes (byte-array (map byte text))
        cipher (get-cipher Cipher/ENCRYPT_MODE key)]
    (base64 (.doFinal cipher bytes))))


(defn decrypt [text key]
  (try
    (let [cipher (get-cipher Cipher/DECRYPT_MODE key)]
      (String. (.doFinal cipher (debase64 text))))
    (catch Exception e
      nil)))
