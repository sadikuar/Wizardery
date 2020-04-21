import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://127.0.0.1:8081/')

WebUI.click(findTestObject('Page_Wizardery/a_Signin'))

WebUI.click(findTestObject('Page_Wizardery/a_here'))

WebUI.setText(findTestObject('Page_Wizardery/input_Email address_email'), 'user@wizardery.ch')

WebUI.setText(findTestObject('Page_Wizardery/input_Username_username'), 'User')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Password_password'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.setEncryptedText(findTestObject('Page_Wizardery/input_Confirm password_passwordConfirm'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.setText(findTestObject('Page_Wizardery/textarea_Description_description'), 'This email is already used')

WebUI.click(findTestObject('Page_Wizardery/input_Public_submit'))

WebUI.click(findTestObject('Object Repository/Page_Wizardery/div_This email is already used'))

