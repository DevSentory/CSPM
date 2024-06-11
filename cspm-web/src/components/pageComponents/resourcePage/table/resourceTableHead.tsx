export default function TableHead() {
  return (
    <div className='grid h-12 w-full grid-cols-17 items-center border-b-2 border-t-2 border-gray-300 bg-gray-200'>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>스캔 날짜</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>
        리소스 생성 날짜
      </div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>고객사</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Account Name</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Acount ID</div>
      <div className='flex justify-center overflow-hidden text-nowrap'>Service</div>
      <div className='col-span-3 flex justify-center overflow-hidden text-nowrap'>Resource ID</div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Tag</div>
      <div className='flex justify-center overflow-hidden text-nowrap'>상세 보기</div>
    </div>
  )
}
