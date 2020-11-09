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

(h/defelem input [{:keys [readonly value validate] :as attr} kids]
  (let [valid   (j/cell= (boolean validate))
        success (j/cell= (when-not readonly (when value valid)))
        warning (j/cell= (when-not readonly (nil? value)))
        failure (j/cell= (when-not readonly (when value (not valid))))]
    (j/cell= (debug "Validate form input %s (%s)" validate valid))
    (form/input
      (dissoc attr :validate)
      :success success
      :danger  failure
      ::form/warning warning
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
