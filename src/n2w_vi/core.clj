(ns n2w-vi.core)

(def digit->word
  (zipmap (map #(first (str %)) (range 10))
          (map str '(không một hai ba bốn năm sáu bảy tám chín))))

(def ^:dynamic *child-group?* false)

(defn group-of-two->word [[y z]]
  (cond
   (and (= \1 y) (= \0 z))
   "mười"

   (= \1 y)
   (str "mười " (digit->word z))

   :default
   (str (digit->word y) " mươi"
        (when (not= \0 z)
          (str " "
               (cond
                (and (= \1 z) (not (#{\0 \1} y)))
                "mốt"
                (and (= \4 z) (not (#{\0 \1} y)))
                "tư"
                (and (= \5 z) (not= \0 y))
                "lăm"
                :default
                (digit->word z)))))))

(defn group-of-three->word [[x y z]]
  (cond
   (and (= \0 x y z))
   (when *child-group?*
     "không trăm")

   (= \0 y z)
   (str (digit->word x) " trăm")

   (= \0 y)
   (str (when-not (and (= \0 x) (not *child-group?*))
          (str (digit->word x) " trăm linh "))
        (digit->word z))

   :default
   (str (when-not (and (= \0 x) (not *child-group?*))
          (str (digit->word x) " trăm "))
        (group-of-two->word [y z]))))

(declare number->word-helper)

(defn number->word [n]
  (number->word-helper (seq (str n))))

(defn link-groups [digits level paster]
  (let [n-of-digits (count digits)
        remainder (rem n-of-digits level)
        digits-to-process (if (zero? remainder)
                            level
                            remainder)
        repeated (dec (/ (- n-of-digits digits-to-process)
                         level))
        head (take digits-to-process digits)
        tail (drop digits-to-process digits)]
    (str (number->word-helper head) " "
         paster
         (if (apply = (cons \0 tail))
           (when (< 0 repeated)
             (apply str " " (interpose " " (repeat repeated paster))))
           (binding [*child-group?* true]
             (str " " (number->word-helper tail)))))))

(defn number->word-helper [digits]
  (let [n-of-digits (count digits)]
    (cond
     (< 9 n-of-digits)
     (link-groups digits 9 "tỷ")

     (< 6 n-of-digits)
     (link-groups digits 6 "triệu")

     (< 3 n-of-digits)
     (link-groups digits 3 "nghìn")

     (= 3 n-of-digits)
     (group-of-three->word digits)

     (= 2 n-of-digits)
     (group-of-two->word digits)

     (= 1 n-of-digits)
     (digit->word (first digits)))))
