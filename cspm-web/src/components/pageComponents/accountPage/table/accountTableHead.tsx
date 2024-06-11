import React from 'react'

interface TableHeadersProps {
  toggleAllCheckboxes: () => void
  allChecked: boolean
}

export default function AccountTableHead({ toggleAllCheckboxes, allChecked }: TableHeadersProps) {
  return (
    <div className='grid h-12 w-full grid-cols-13 items-center border-b-2 border-t-2 border-gray-300 bg-gray-200'>
      <div className='flex justify-center overflow-hidden text-nowrap'>
        <input
          id='account-all-checkbox'
          name='account-all-checkbox'
          type='checkbox'
          checked={allChecked}
          onChange={toggleAllCheckboxes}
        />
      </div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>고객사</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Account Name</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Account ID</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>
        Acount 등록 시간
      </div>

      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>
        최근 리소스 스캔 시간
      </div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>스캔 결과</div>
    </div>
  )
}
