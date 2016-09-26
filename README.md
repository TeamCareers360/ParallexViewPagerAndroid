﻿# ParallexViewPagerAndroid

The purpose of this library is to create the parallax effect for header space with ViewPager and Fragments that contains either ListView or ScrollView in which the header space will animate while scrolling either upward or downwards direction.

We have seen other parallax header library for my purpose, but none of them fulfil my requirements like:

1) Effect should be smooth.
2) And if there is less data in any tab inside ViewPager then scrolling should not be enable for that tab. 

But whenever I came to less content tab from other tab after scrolling upward (tab having enough data to scroll up), tab won’t jump to default direction.
This library solves the above issues and provide smooth parallax experience.

