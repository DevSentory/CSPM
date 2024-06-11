import AccountCheckDeleteButton from '@/components/button/accountCheckDeleteButton'
import AccountCheckCreateButton from '@/components/button/accountCheckCreateButton'
import AccountCheckUpdateButton from '@/components/button/accountCheckUpdateButton'
import ExcelDownloadButtion from '@/components/button/excelDownloadButtion'
import FillterResetButtion from '@/components/button/fillterResetButtion'
import AccountScanStartButton from '@/components/button/accountScanStartButton'
import SearchBox from '@/components/searchBox/searchBox'
import AccountNameSelect from '@/components/selectBox/accountNameSelectBox'
import ClientSelectBox from '@/components/selectBox/clientSelectBox'

export default function AccountMultiFilterMenuBar() {
  const screen = 'screen3'
  return (
    <div className='grid h-28 w-full grid-cols-4 grid-rows-2 gap-3 '>
      <div className='grid-col-style'>
        <ClientSelectBox />
      </div>
      <div className='grid-col-style'>
        <AccountNameSelect />
      </div>
      <div className='grid-col-style'>
        <SearchBox />
      </div>
      <div className='grid-col-style justify-center space-x-5'>
        <FillterResetButtion />
        <AccountScanStartButton />
        <ExcelDownloadButtion screenType={screen} />
      </div>
      <div className='grid-col-style col-span-4 justify-start space-x-5'>
        <AccountCheckCreateButton />
        <AccountCheckUpdateButton />
        <AccountCheckDeleteButton />
      </div>
    </div>
  )
}
