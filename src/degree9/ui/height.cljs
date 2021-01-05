(ns degree9.ui.height
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.object :as obj]
            [degree9.browser.window :as win]
            [uikit-hl.height :as height]
            [uikit-hl.utility :as util]))

(defn- dimensions [el]
  (.getBoundingClientRect el))

(defn- window-height [win]
  (.-innerHeight win))

(defn- document-height [el]
  (let [doc el.documentElement]
    (max (.-offsetHeight doc) (.-scrollHeight doc))))

(defn- style [el style]
  (obj/get (.-style el) style))

(defn- element-height [el]
  (let [current (style el :height)]
    (if (= "auto" current)
        (style el :offsetHeight)
        current)))

(defn- height [el]
  (cond
    (= js/window el)   (window-height el)
    (= js/document el) (document-height el)
    :else              (element-height el)))

(defn- offset-height [el]
  (.-height (dimensions el)))

(defn- expand-height [elem]
  (let [win (window-height js/window)
        doc (offset-height js/document.documentElement)
        current (offset-height elem)]
    (prn win doc current (- win (- doc current)))
    (obj/set-in elem [:style :minHeight] (- win (- doc current)))))

(defmethod h/do! :expand-height
  [elem kw v]
  ;; formula to calculate height
  ;; window height - (offset document height - offset current element height ) - box model adjustment
  (let [expand #(expand-height elem)]
    (expand)
    (if v
      (win/listen :resize expand)
      (win/unlisten :resize expand))))


(h/defelem viewport [attr kids]
  (util/panel
    attr
    ::height/viewport {:expand true}
    kids))

(defmulti height! h/kw-dispatcher :default ::default)

(defmethod h/do! ::default
  [elem kw v]
  (height/uk-height! elem kw v))

(defmethod height! ::full
  [elem kw v]
  (h/do! elem ::height/height-1-1 v))
