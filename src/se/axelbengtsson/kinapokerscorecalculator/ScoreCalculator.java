package se.axelbengtsson.kinapokerscorecalculator;

import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.Bonus;
import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.GameType;
import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.Hand;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ScoreCalculator extends Activity {
  KinaPokerScoreCalculator sc;
  final int[] hands = { R.id.hand1_1, R.id.hand1_2, R.id.hand1_3, R.id.hand1_4,
      R.id.hand2_1, R.id.hand2_2, R.id.hand2_3, R.id.hand2_4,
      R.id.hand3_1, R.id.hand3_2, R.id.hand3_3, R.id.hand3_4,
      R.id.kind_you, R.id.kind_two, R.id.kind_three, R.id.kind_four,
      R.id.fullhouse_you, R.id.fullhouse_two, R.id.fullhouse_three, R.id.fullhouse_four,
      R.id.fourofakind_you, R.id.fourofakind_two, R.id.fourofakind_three, R.id.fourofakind_four,
      R.id.straightflush_you, R.id.straightflush_two, R.id.straightflush_three, R.id.straightflush_four,
      R.id.fourofakind_you2, R.id.fourofakind_two2, R.id.fourofakind_three2, R.id.fourofakind_four2,
      R.id.straightflush_you2, R.id.straightflush_two2, R.id.straightflush_three2, R.id.straightflush_four2,
      R.id.scoopup_by_two, R.id.scoopup_by_three, R.id.scoopup_by_four,
      R.id.scoopup_is_two, R.id.scoopup_is_three, R.id.scoopup_is_four};
  final int[] gameTypeSpinners = { R.id.you_spinner, R.id.opponent_1_spinner,
      R.id.opponent_2_spinner, R.id.opponent_3_spinner };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_calculator);

    //Setup Gametype
    for (int i : gameTypeSpinners) {
      setupGameTypeSpinner(i);
    }
    setVisibilityOnGameTypeSpinner(View.GONE, View.GONE, View.GONE, View.GONE);
    // Remove hands
    setVisibilityOnHands(View.GONE, 0);
    // Reset radiobuttons, checkbox, ...
    resetView();
  }

// Is this used to something?
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.score_calculator, menu);
    return true;
  }
/**
 * Reset the all view
 */
  private void resetView() {
    for (int id : this.hands) {
      if (findViewById(id) instanceof RadioButton) {
        ((RadioButton)findViewById(id)).setChecked(false);
      } else if (findViewById(id) instanceof CheckBox) {
        ((CheckBox)findViewById(id)).setChecked(false);
      }
    }
    for (int id : this.gameTypeSpinners) {
      ((Spinner)findViewById(id)).setSelection(0);
    }
  }

  /**
   * Change the number of players
   * @param view
   */
  public void onSelectPlayersButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();
    if (checked) {
      // Check which radio button was clicked
      switch (view.getId()) {
      case R.id.one:
        Log.d("kpsc", "choosed 1 players");
        setVisibilityOnGameTypeSpinner(View.VISIBLE, View.GONE, View.GONE, View.GONE);
        sc = KinaPokerScoreCalculator.create(1);
        break;
      case R.id.two:
        Log.d("kpsc", "choosed 2 players");
        setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        sc = KinaPokerScoreCalculator.create(2);
        break;
      case R.id.three:
        Log.d("kpsc", "choosed 3 players");
        setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE);
        sc = KinaPokerScoreCalculator.create(3);
        break;
      case R.id.four:
        Log.d("kpsc", "choosed 4 players");
        setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
        sc = KinaPokerScoreCalculator.create(4);
        break;
      }
      //Default is playing
      setVisibilityOnHands(View.VISIBLE, sc.getNumberOfPlayerThatPlay());
      resetView();
      displaySum();
     }
  }
  public void onCheckboxClickedHand1(View view) {
    Log.d("hand", "Hand1=" + getIntFromRadioBution(view));
    sc.setPlaceOfHand(Hand.Hand1, getIntFromRadioBution(view));
    displaySum();
  }
  public void onCheckboxClickedHand2(View view) {
    sc.setPlaceOfHand(Hand.Hand2, getIntFromRadioBution(view));
    Log.d("hand", "Hand2=" + getIntFromRadioBution(view));
    displaySum();
  }
  public void onCheckboxClickedHand3(View view) {
    sc.setPlaceOfHand(Hand.Hand3, getIntFromRadioBution(view));
    Log.d("hand", "Hand3=" + getIntFromRadioBution(view));
    displaySum();
  }

  private int getIntFromRadioBution(View view) {
    int ret = 0;
    boolean checked = ((RadioButton) view).isChecked();
    if (checked) {
      switch (view.getId()) {
      case R.id.hand1_1:
      case R.id.hand2_1:
      case R.id.hand3_1:
        ret = 1;
        break;
      case R.id.hand1_2:
      case R.id.hand2_2:
      case R.id.hand3_2:
        ret = 2;
        break;
      case R.id.hand1_3:
      case R.id.hand2_3:
      case R.id.hand3_3:
        ret = 3;
        break;
      case R.id.hand1_4:
      case R.id.hand2_4:
      case R.id.hand3_4:
        ret = 4;
        break;
      }
    }
    return ret;
  }
  private void displaySum() {
    Log.d("SUM", sc.getSum() + "");
    ((TextView)findViewById(R.id.sum)).setText(sc.getSum() + "");
  }
  private void setVisibilityOnGameTypeSpinner(final int you, final int opt1, final int opt2, final int opt3) {
    setVisiabilityOnGameTypeSpinner(R.id.you, R.id.you_spinner, you);
    setVisiabilityOnGameTypeSpinner(R.id.opponent_1, R.id.opponent_1_spinner, opt1);
    setVisiabilityOnGameTypeSpinner(R.id.opponent_2, R.id.opponent_2_spinner, opt2);
    setVisiabilityOnGameTypeSpinner(R.id.opponent_3, R.id.opponent_3_spinner, opt3);
  }
  private void setVisiabilityOnGameTypeSpinner(int idText, int idSpinner, final int show) {
    findViewById(idText).setVisibility(show);
    findViewById(idSpinner).setVisibility(show);
  }
  private void setVisibilityOnHands(final int show, final int players) {
    final int[] textId = {R.id.hand1, R.id.hand2, R.id.hand3,
                           R.id.kind, R.id.fullhouse, R.id.fourofakind, R.id.straightflush,
                           R.id.fourofakind2, R.id.straightflush2,
                           R.id.line0, R.id.line1, R.id.line2, R.id.line3,
                           R.id.scoopup_is, R.id.scoopup_by};
    for (int hand : textId) {
      findViewById(hand).setVisibility(show);
    }
    for (int hand : hands) {
      findViewById(hand).setVisibility(View.GONE);
    }
    if (show == View.VISIBLE) {
      switch(players) {
      case 4:
        findViewById(R.id.hand1_4).setVisibility(show);
        findViewById(R.id.hand2_4).setVisibility(show);
        findViewById(R.id.hand3_4).setVisibility(show);
        //Bonus
        findViewById(R.id.kind_four).setVisibility(show);
        findViewById(R.id.fullhouse_four).setVisibility(show);
        findViewById(R.id.fourofakind_four).setVisibility(show);
        findViewById(R.id.straightflush_four).setVisibility(show);
        findViewById(R.id.fourofakind_four2).setVisibility(show);
        findViewById(R.id.straightflush_four2).setVisibility(show);
        // Scoop
        findViewById(R.id.scoopup_by_four).setVisibility(show);
        findViewById(R.id.scoopup_is_four).setVisibility(show);
      case 3:
        findViewById(R.id.hand1_3).setVisibility(show);
        findViewById(R.id.hand2_3).setVisibility(show);
        findViewById(R.id.hand3_3).setVisibility(show);
        //Bonus
        findViewById(R.id.kind_three).setVisibility(show);
        findViewById(R.id.fullhouse_three).setVisibility(show);
        findViewById(R.id.fourofakind_three).setVisibility(show);
        findViewById(R.id.straightflush_three).setVisibility(show);
        findViewById(R.id.fourofakind_three2).setVisibility(show);
        findViewById(R.id.straightflush_three2).setVisibility(show);
        // Scoop
        findViewById(R.id.scoopup_by_three).setVisibility(show);
        findViewById(R.id.scoopup_is_three).setVisibility(show);
      case 2:
        findViewById(R.id.hand1_2).setVisibility(show);
        findViewById(R.id.hand2_2).setVisibility(show);
        findViewById(R.id.hand3_2).setVisibility(show);
        //Bonus
        findViewById(R.id.kind_two).setVisibility(show);
        findViewById(R.id.fullhouse_two).setVisibility(show);
        findViewById(R.id.fourofakind_two).setVisibility(show);
        findViewById(R.id.straightflush_two).setVisibility(show);
        findViewById(R.id.fourofakind_two2).setVisibility(show);
        findViewById(R.id.straightflush_two2).setVisibility(show);
        // Scoop
        findViewById(R.id.scoopup_by_two).setVisibility(show);
        findViewById(R.id.scoopup_is_two).setVisibility(show);
      case 1:
        findViewById(R.id.hand1_1).setVisibility(show);
        findViewById(R.id.hand2_1).setVisibility(show);
        findViewById(R.id.hand3_1).setVisibility(show);
        //Bonus
        findViewById(R.id.kind_you).setVisibility(show);
        findViewById(R.id.fullhouse_you).setVisibility(show);
        findViewById(R.id.fourofakind_you).setVisibility(show);
        findViewById(R.id.straightflush_you).setVisibility(show);
        findViewById(R.id.fourofakind_you2).setVisibility(show);
        findViewById(R.id.straightflush_you2).setVisibility(show);
      }
    }
  }
  private void setupGameTypeSpinner(int id) {
    Spinner spinner = (Spinner) findViewById(id);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
         R.array.game_type, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new SpinnerActivity(id));
  }
  public void onCheckboxClickedKind(View view) {
    setBonus(view, Bonus.Kind, Hand.Hand1, R.id.kind_you, R.id.kind_two,
        R.id.kind_three, R.id.kind_four);
    displaySum();
  }
  public void onCheckboxClickedFullHouse(View view) {
    setBonus(view, Bonus.FullHouse, Hand.Hand2, R.id.fullhouse_you, R.id.fullhouse_two,
        R.id.fullhouse_three, R.id.fullhouse_four);
    displaySum();
  }
  public void onCheckboxClickedFourOfAKind(View view) {
    setBonus(view, Bonus.FourOfAKind, Hand.Hand2, R.id.fourofakind_you, R.id.fourofakind_two,
        R.id.fourofakind_three, R.id.fourofakind_four);
    displaySum();
  }
  public void onCheckboxClickedStraightFlush(View view) {
    setBonus(view, Bonus.StraightFlush, Hand.Hand2, R.id.straightflush_you, R.id.straightflush_two,
        R.id.straightflush_three, R.id.straightflush_four);
    displaySum();
  }
  public void onCheckboxClickedFourOfAKind2(View view) {
    setBonus(view, Bonus.FourOfAKind, Hand.Hand3, R.id.fourofakind_you2, R.id.fourofakind_two2,
        R.id.fourofakind_three2, R.id.fourofakind_four2);
    displaySum();
  }
  public void onCheckboxClickedStraightFlush2(View view) {
    setBonus(view, Bonus.StraightFlush, Hand.Hand3, R.id.straightflush_you2, R.id.straightflush_two2,
        R.id.straightflush_three2, R.id.straightflush_four2);
    displaySum();
  }
  public void onCheckboxClickedIsScooping(View view) {
    if (((CheckBox) view).isChecked()) {
      sc.setBonusYou(Hand.Hand1, Bonus.ScoopUp);
    } else {
      sc.removeBonusYou(Hand.Hand1,Bonus.ScoopUp);
    }
    displaySum();
  }
  public void onCheckboxClickedScoopedBy(View view) {
    if (((CheckBox) view).isChecked()) {
      sc.setBonusOpponent(Hand.NoHand, Bonus.ScoopUp);
    } else {
      sc.removeBonusOpponent(Hand.NoHand,Bonus.ScoopUp);
    }
    displaySum();
  }
  /**
   * @param view
   * @return
   */
  private boolean setBonus(final View view, final Bonus bonus, final Hand hand, final int you, final int op1, final int op2, final int op3) {
    final boolean checked = ((CheckBox) view).isChecked();
    final int id = view.getId();
    if (checked && id == you) {
      sc.setBonusYou(hand, bonus);
    } else if (id == you){
        sc.removeBonusYou(hand, bonus);
    } else if (checked && (op1 == id || op2 == id || op3 == id)) {
      sc.setBonusOpponent(hand, bonus);
    } else if (op1 == id || op2 == id || op3 == id) {
        sc.removeBonusOpponent(hand, bonus);
    } else {
      Log.e(bonus.toString(), "Should not come here id = " + id);
    }
    Log.d(bonus.toString(),"hand=" + hand.toString() + " checked=" + checked);
    return checked;
  }
  // GAME TYPE
  public class SpinnerActivity extends Activity implements OnItemSelectedListener {
    private int spinnerId;
    public SpinnerActivity(int id) {
      spinnerId = id;
    }
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
      Log.d("kpsc", "SpinnerId" + spinnerId + "pos=" + pos + " id=" + id + " name" + parent.getItemAtPosition(pos));
        // An item was selected. You can retrieve the selected item using
        GameType gt = GameType.valueOf(((String)parent.getItemAtPosition(pos)).replace(" ", ""));
        if (sc != null) {
        switch (spinnerId) {
        case R.id.you_spinner:
          sc.setGamePlay(0, gt);
          if (gt != GameType.Play) {
            setVisibilityOnHands(View.GONE, sc.getNumberOfPlayerThatPlay());
          }
          break;
        case R.id.opponent_1_spinner:
          sc.setGamePlay(1, gt);
          break;
        case R.id.opponent_2_spinner:
          sc.setGamePlay(2, gt);
          break;
        case R.id.opponent_3_spinner:
          sc.setGamePlay(3, gt);
          break;
        }
        if (sc.areYouPlaying()) {
          setVisibilityOnHands(View.VISIBLE, sc.getNumberOfPlayerThatPlay());
        }
        displaySum();
      }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
}
