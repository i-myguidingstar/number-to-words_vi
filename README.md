# number-to-words_vietnamese

A Clojure library for number to Vietnamese words conversion.

## Installation

![Latest version](https://clojars.org/n2w-vi/latest-version.svg)

## Usage

```clojure
(require '[n2w-vi.core :refer [number->words]])

;; Light travels 299,793 kilometers per second.
;; Distance from Sun to Pluto is said to be 5.5 light-hours, or in Vietnamese (kilometers):
(let [distance-from-Sun-to-Pluto ;; 5935901400
      (bigint (* 299793 5.5 60 60))]
  (number->words distance-from-Sun-to-Pluto))
;; => "năm tỷ chín trăm ba mươi lăm triệu chín trăm linh một nghìn bốn trăm"
```

If the number is too long to be stored as a integer, you may want to use
the function `number->words*` which receives a sequence of digits
(of char type, eg \0, \1 etc) instead.

```clojure
(number->words* [\1 \2 \3])
;; => "một trăm hai mươi ba"
```

You may want to have commas as group separators.

```clojure
(number->words 123456789 ",")
;; => "một trăm hai mươi ba triệu, bốn trăm năm mươi sáu nghìn bảy trăm tám mươi chín"

(number->words* [\1 \2 \3 \4 \5 \6 \7 \8 \9] ",")
;; => "một trăm hai mươi ba triệu, bốn trăm năm mươi sáu nghìn bảy trăm tám mươi chín"
```

See provided unit tests for more usage.

Enjoy!

## License

Copyright © 2014 Hoang Minh Thang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
