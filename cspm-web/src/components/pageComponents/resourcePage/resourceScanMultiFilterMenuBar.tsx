import ExcelDownloadButtion from '@/components/button/excelDownloadButtion'
import FillterResetButtion from '@/components/button/fillterResetButtion'
import CalendarSelect from '@/components/calendarComponents/calendarSelect'
import SearchBox from '@/components/searchBox/searchBox'
import AccountIdSelect from '@/components/selectBox/accountIdSelectBox'
import AccountNameSelect from '@/components/selectBox/accountNameSelectBox'
import ClientSelectBox from '@/components/selectBox/clientSelectBox'
import ResourceSelect from '@/components/selectBox/resourceSelectBox'
import ServiceSelect from '@/components/selectBox/serviceSelectBox'

export default function ResourceScanMultiFilterMenuBar() {
  const screen = 'screen1'
  return (
    <div className='grid h-36 w-full grid-cols-8 grid-rows-3 gap-3 '>
      <div className='grid-col-style col-span-2'>
        <ClientSelectBox />
      </div>

      <div className='grid-col-style col-span-2'>
        <AccountNameSelect />
      </div>

      <div className='grid-col-style col-span-2'>
        <AccountIdSelect />
      </div>

      <div className='grid-col-style col-span-2 space-x-4'>
        <ServiceSelect />
      </div>

      <div className='grid-col-style col-span-4 space-x-4'>
        <CalendarSelect />
      </div>

      <div className='grid-col-style col-span-2'>
        <ResourceSelect />
      </div>

      <div className='grid-col-style col-span-2'>
        <SearchBox />
      </div>

      <div className='col-span-6'></div>

      <div className='grid-col-style col-span-2 justify-end space-x-7'>
        <FillterResetButtion />
        <ExcelDownloadButtion screenType={screen}/>
      </div>
    </div>
  )
}
