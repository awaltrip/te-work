<h1>Vending Machine Application</h1>
<p>In this pair-programming project, Jason Ronis and I created a command-line application in Java which mimics the properties 
  and functionality of a vending machine.</p>
<p>The requirements for this project can be found <a href="https://github.com/awaltrip/te-work/blob/master/vending-machine/Module%201%20Capstone-Vending%20Machine%20Terminal.pdf">here</a>.</p>
<p>We were provided with a very simple VendingMachineCLI class and Menu class to get started. We expanded upon those classes, and added two more:</p>
<ol>
  <li>A VendingMachine class to hold methods and attributes that a real machine would have, such as hold inventory and manage the monetary balance.</li>
  <li>An Item class to hold the attributes of each item in the inventory - name, machine slot #, price, and quantity remaining.</li>
</ol>
<p>Functionality we achieved includes:</p>
<ul>
  <li>Vending machine inventory is stocked via an input file (CSV); inventory includes chips, candy, beverages, and gum.</li>
  <li>The user can choose to have the machine display a list of items, including name, price, and 
    quantity remaining in the machine.</li>
  <li>The user can feed the machine money, choose items to purchase, end the transaction and get change.</li>
  <li>All monetary transactions are logged in a text file for auditing purposes.</li>
  <li>JUnit tests and manual tests were performed to drive development, assure the app functions correctly, and will not break from bad user input.</li>
</ul>
