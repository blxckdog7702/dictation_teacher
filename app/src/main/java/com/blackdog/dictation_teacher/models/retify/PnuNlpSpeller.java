package com.blackdog.dictation_teacher.models.retify;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PnuNlpSpeller
{
    @SerializedName("pnuErrorWordList")
    @Expose
    private PnuErrorWordList[] pnuErrorWordList;

    public PnuErrorWordList[] getPnuErrorWordList ()
    {
        return pnuErrorWordList;
    }

    public void setPnuErrorWordList (PnuErrorWordList[] PnuErrorWordList)
    {
        this.pnuErrorWordList = PnuErrorWordList;
    }

}
