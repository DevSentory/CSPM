import ExcelDownloadButtion from '@/components/button/excelDownloadButtion'
import FillterResetButtion from '@/components/button/fillterResetButtion'
import KeyInResultButton from '@/components/button/keyinResultButtion'
import CalendarSelect from '@/components/calendarComponents/calendarSelect'
import SearchBox from '@/components/searchBox/searchBox'
import AccountIdSelect from '@/components/selectBox/accountIdSelectBox'
import AccountNameSelect from '@/components/selectBox/accountNameSelectBox'
import ClientSelectBox from '@/components/selectBox/clientSelectBox'
import WeakRatingSelectBox from '@/components/selectBox/policyServeritySelectBox'
import WeakStsSelectBox from '@/components/selectBox/vulnerabilityStatus'

export default function MultiFilterMenuBar() {
  const screen = 'screen2'
  return (
    <div className='h-35 grid w-full grid-cols-8 grid-rows-3 gap-3 '>
      <div className='col-span-2 flex h-full w-full items-center justify-start'>
        <ClientSelectBox />
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-start'>
        <AccountNameSelect />
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-start '>
        <AccountIdSelect />
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-end space-x-4'>
        <WeakRatingSelectBox />
      </div>

      <div className='col-span-4 flex h-full w-full items-center justify-end space-x-4'>
        <CalendarSelect />
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-start'>
        <WeakStsSelectBox />
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-center'>
        <SearchBox />
      </div>

      <div className='col-span-6'></div>
      <div className='col-span-2 flex h-full w-full items-center justify-end space-x-7'>
        <FillterResetButtion />
        <KeyInResultButton />
        <ExcelDownloadButtion screenType={screen} />
      </div>
    </div>
  )
}
