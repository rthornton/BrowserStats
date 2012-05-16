(ns BrowserAnalysis.core)

(use '(incanter core charts excel stats))

; Working without percentage.
(defn main [& args]
 (with-data
   (read-xls "browsersSimple.xls")
   (let [sum (sum ($ :Total))
         percentage (map #(/ 100 %1) $)]
     (str percentage))
))
