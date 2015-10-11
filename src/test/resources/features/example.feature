Tags: @all @example

Feature: An example feature, where you navigate the G2G3 Digital website.

  Scenario: You browse through the G2G3 Digital website
    Given I go to the home page
    Then the page title is "Home | G2G3.DIGITAL"
      And if I click the navigation link "1" with label "CAPABILITIES"
    Then the page title is "Capabilities | G2G3.DIGITAL"
      And if I click the navigation link "2" with label "RESULTS"
    Then the page title is "Results | G2G3.DIGITAL"
      And if I click the navigation link "3" with label "BLOG"
    Then the page title is "Blog | G2G3.DIGITAL"
      And if I click the navigation link "4" with label "JOBS"
    Then the page title is "Jobs | G2G3.DIGITAL"
      And if I click the navigation link "5" with label "CONTACT US"
    Then the page title is "Contact us | G2G3.DIGITAL"
