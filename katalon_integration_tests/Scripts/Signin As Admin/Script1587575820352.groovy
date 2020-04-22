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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://127.0.0.1:8081/')

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/a_Signin'))

WebUI.setText(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/input_Email address_email'), 'admin@wizardery.ch')

WebUI.setEncryptedText(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/input_Password_password'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/input_here_submit'))

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/img_Admin page_img-thumbnail wiz-avatar'))

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/h2_Administrator'))

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/a_Logout'))

WebUI.click(findTestObject('Object Repository/signin_as_admin/Page_Wizardery/h2_Signin'))

WebUI.closeBrowser()

