(ns n2w-vi.core-test
  (:require [clojure.test :refer :all]
            [n2w-vi.core :refer :all]))

(deftest number->words*-test
  (testing "n->w*"
    (is (= (number->words*
            (map #(first (str %))
                 (drop 1 (take 28 (cycle (range 10))))))
           (str "một trăm hai mươi ba triệu"
                " bốn trăm năm mươi sáu nghìn"
                " bảy trăm tám mươi chín tỷ"
                " không trăm mười hai triệu"
                " ba trăm bốn mươi lăm nghìn"
                " sáu trăm bảy mươi tám tỷ"
                " chín trăm linh một triệu"
                " hai trăm ba mươi tư nghìn"
                " năm trăm sáu mươi bảy")))

    (is (= (number->words*
            (map #(first (str %))
                 (cons 9 (repeat 9 0))))
           "chín tỷ"))

    (is (= (number->words*
            (map #(first (str %))
                 (cons 9 (repeat 18 0))))
           "chín tỷ tỷ"))

    (is (= (number->words*
            (map #(first (str %))
                 (cons 9 (repeat 27 0))))
           "chín tỷ tỷ tỷ"))

    (is (= (number->words*
            (map #(first (str %))
                 (cons 6 (repeat 6 0))))
           "sáu triệu"))

    (is (= (number->words*
            (map #(first (str %))
                 (cons 3 (repeat 3 0))))
           "ba nghìn"))

    (is (= (number->words*
            (map #(first (str %))
                 (concat (cons 9 (repeat 8 0)) '(4))))
           (str "chín tỷ không triệu không nghìn"
                " không trăm linh bốn")))

    (is (= (number->words*
            (map #(first (str %))
                 '(1 0 3 6 0 6 7 2 0 8 1 3 1 9 4)))
           (str "một trăm linh ba nghìn"
                " sáu trăm linh sáu tỷ"
                " bảy trăm hai mươi triệu"
                " tám trăm mười ba nghìn"
                " một trăm chín mươi tư")))))

(deftest group-of-two->words-tests
  (testing "testing numbers with two digits"
    (is (= (group-of-two->words \4 \7)
           "bốn mươi bảy"))
    (is (= (group-of-two->words \2 \0)
           "hai mươi"))))

(deftest group-of-three->words-tests
  (testing "testing numbers with three digits"
    (is (= (group-of-three->words \3 \4 \7)
           "ba trăm bốn mươi bảy"))
    (is (= (group-of-three->words \3 \1 \7)
           "ba trăm mười bảy"))
    (is (= (group-of-three->words \3 \0 \0)
           "ba trăm"))
    (is (= (group-of-three->words \3 \0 \7)
           "ba trăm linh bảy"))
    (is (= (group-of-three->words \3 \1 \0)
           "ba trăm mười")))

  (testing "testing child-group numbers with less than two digits"
    (is (= (group-of-three->words \0 \0 \0)
           "không"))
    (is (= (group-of-three->words \0 \0 \6)
           "không trăm linh sáu"))
    (is (= (group-of-three->words \0 \4 \7)
           "không trăm bốn mươi bảy"))
    (is (= (group-of-three->words \0 \2 \0)
           "không trăm hai mươi"))))
