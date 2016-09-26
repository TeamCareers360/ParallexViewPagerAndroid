package com.careers.parallaxeffectlib.listeners;

/**
 * Created by Abha Dhiman.
 * <p>Interface used to notify Header height to fragment</p>
 */
public interface OnFragmentSelectedListener {
  /**
   * @param lHeaderHeight : header view height
   * <p>When height is set dynamically to view ,it updates the fragments</p>
   */
  public void onFragmentSelected(int lHeaderHeight);


}
