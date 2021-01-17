(ns degree9.ui.form
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.debug :as dbg]
            [uikit-hl.form :as form]
            [uikit-hl.flex :as flex]
            [uikit-hl.grid :as grid]
            [uikit-hl.margin :as margin]
            [uikit-hl.width :as width]
            [degree9.regex :as regex]))

(dbg/defdebug debug "degree9:experience:forms")

(defn form-cell [default]
  (let [fcell (j/cell nil)]
    (j/cell= (or fcell default) (partial reset! fcell))))

(defn field-cell [data & keys]
  (j/cell= (get-in data keys) (partial swap! data assoc-in keys)))

(h/defelem form [attr kids]
  (form/form attr kids))

(h/defelem fieldset [{:keys [legend] :as attr} kids]
  (form/fieldset
    (dissoc attr :legend)
    (when legend legend)
    kids))

(h/defelem label [attr kids]
  (form/label attr kids))

(h/defelem legend [attr kids]
  (form/legend attr kids))

(h/defelem controls [attr kids]
  (form/controls attr kids))

(h/defelem input [{:keys [readonly value validate] :as attr} kids]
  (let [valid   (j/cell= (boolean validate))
        success (j/cell= (when-not readonly (when value valid)))
        warning (j/cell= (when-not readonly (nil? value)))
        failure (j/cell= (when-not readonly (when value (not valid))))]
    (form/input
      (dissoc attr :validate)
      :success success
      :danger  failure
      ::form/warning warning
      kids)))

(h/defelem select [{:keys [options readonly value validate] :as attr} kids]
  (let [valid   (j/cell= (boolean validate))
        success (j/cell= (when-not readonly (when value valid)))
        warning (j/cell= (when-not readonly (nil? value)))
        failure (j/cell= (when-not readonly (when value (not valid))))]
    (j/cell= (prn value validate valid success warning failure))
    (form/select
      (dissoc attr :options :validate)
      :success success
      :danger  failure
      ::form/warning warning
      (when options
        (h/option :selected (j/cell= (nil? value)) :disabled true :hidden true ""))
      (when options
        (for [opt options]
          (h/option :selected (j/cell= (= opt value)) opt)))
      kids)))

(h/defelem number [attr kids]
  (input
    attr
    :type "number"
    :pattern regex/number
    kids))

(h/defelem email [attr kids]
  (input
    attr
    :type "email"
    :pattern regex/email
    kids))

(h/defelem phone-number [attr kids]
  (input
    attr
    :type "tel"
    :pattern regex/phone-number
    kids))

(h/defelem field [{:keys [type] :as attr} kids]
  (case type
    "number" (number attr kids)
    "email"  (email attr kids)
    "select" (select attr kids)
    "tel"    (phone-number attr kids)
    (input attr kids)))
